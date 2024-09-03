package com.example.gymsaround

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel() : ViewModel() {

    private var gymsDao = GymsDatabase.getDaoInstance(GymsApplication.getApplicationContext())

    // The state that holds the list of gyms, initially restored from saved state.
    var state by mutableStateOf(emptyList<GymData>())
    private val errorHandle = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    private var apiService: GymsAPIService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://gyms-cario-default-rtdb.firebaseio.com/")
            .build()
        apiService = retrofit.create(GymsAPIService::class.java)

        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandle) {

            state = getAllGyms()
        }
    }

    private suspend fun getAllGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymsDao.getAll().isEmpty())
                throw Exception("Something is wrong ,no Data was found ,try connection to internet")
        }
        gymsDao.getAll()
    }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymsDao.getFavouriteGyms()

        gymsDao.addAll(gyms)

        gymsDao.updateAll(
            favouriteGymsList.map { GymFavouriteState(it.id, true) }
        )
    }


    // Toggles the favourite state of a gym based on its ID.
    fun toggleFavouriteState(gymId: Int) {

        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        val stateIconIsFavourite = gyms[itemIndex].isFavourite

        viewModelScope.launch {
            val updateGymsList = toggleFavouriteGym(gymId, !stateIconIsFavourite)
            state = updateGymsList
        }

    }

    // Toggles the favourite state only  of a gym based on its ID.
    suspend fun toggleFavouriteGym(gymId: Int, newFavouriteState: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.update(
                GymFavouriteState(
                    id = gymId,
                    isFavourite = newFavouriteState
                )
            )
            return@withContext gymsDao.getAll()
        }
}

//    // Stores the favourite state of a gym.
//    private fun storeSelectedGyms(gym: GymData) {
//        val stateHandleList = stateHandle.get<List<Int>?>(VAF_IDS).orEmpty().toMutableList()
//        if (gym.isFavourite) {
//            stateHandleList.add(gym.id)
//        } else {
//            stateHandleList.remove(gym.id)
//        }
//        stateHandle[VAF_IDS] = stateHandleList
//    }
//
//    // Restores the favourite state of gyms from the saved state.
//    private fun List<GymData>.restoreSelectedGyms(): List<GymData> {
//        val gyms = this
//        stateHandle.get<List<Int>?>(VAF_IDS)?.let { saveIds ->
//            val gymsMap = gyms.associateBy { it.id }.toMutableMap() //Map <Int,Gym>
//            saveIds.forEach { gymId->
//                val gym =gymsMap[gymId]?:return@forEach
//                gymsMap[gymId] = gym.copy(isFavourite = true)
//            }
//            return gymsMap.values.toList()
//
//        }
//        return gyms
//    }
//
//    companion object {
//        const val VAF_IDS = "GymsFavouriteIds"
//    }

