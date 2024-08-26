package com.example.gymsaround

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsViewModel(private val stateHandle: SavedStateHandle) : ViewModel() {

    // The state that holds the list of gyms, initially restored from saved state.
    var state by mutableStateOf(emptyList<GymsData>())
    val errorHandle = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    private var apiService:GymsAPIService
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://gyms-cario-default-rtdb.firebaseio.com/")
            .build()
        apiService=retrofit.create(GymsAPIService::class.java)

        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandle) {
            val gyms = getGymsFromRemoteDB()
            state = gyms.restoreSelectedGyms()
        }
    }

    private suspend fun getGymsFromRemoteDB() = withContext(Dispatchers.IO) { apiService.getGyms() }



    // Toggles the favourite state of a gym based on its ID.
    fun toggleFavouriteState(gymId: Int) {
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }

        if (itemIndex != -1) {
            val updatedGym = gyms[itemIndex].copy(isFavourite = !gyms[itemIndex].isFavourite)
            gyms[itemIndex] = updatedGym
            storeSelectedGyms(updatedGym)
            state = gyms
        }
    }

    // Stores the favourite state of a gym.
    private fun storeSelectedGyms(gym: GymsData) {
        val stateHandleList = stateHandle.get<List<Int>?>(VAF_IDS).orEmpty().toMutableList()
        if (gym.isFavourite) {
            stateHandleList.add(gym.id)
        } else {
            stateHandleList.remove(gym.id)
        }
        stateHandle[VAF_IDS] = stateHandleList
    }

    // Restores the favourite state of gyms from the saved state.
    private fun List<GymsData>.restoreSelectedGyms(): List<GymsData> {
        val gyms = this
        stateHandle.get<List<Int>?>(VAF_IDS)?.forEach { gymId ->
            gyms.find { it.id == gymId }?.isFavourite = true
        }
        return gyms
    }

    companion object {
        const val VAF_IDS = "GymsFavouriteIds"
    }


}
