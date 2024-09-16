package com.example.gymsaround.gyms.data

import com.example.gymsaround.GymsApplication
import com.example.gymsaround.gyms.data.local.GymsDatabase
import com.example.gymsaround.gyms.data.local.LocalGymData
import com.example.gymsaround.gyms.data.local.LocalGymFavouriteState
import com.example.gymsaround.gyms.data.remote.GymsAPIService
import com.example.gymsaround.gyms.domain.GymData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsListRepository {
    private var apiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://gyms-cario-default-rtdb.firebaseio.com/")
        .build()
        .create(GymsAPIService::class.java)

    private var gymsDao = GymsDatabase.getDaoInstance(GymsApplication.getApplicationContext())

    suspend fun toggleFavouriteGym(gymId: Int, state: Boolean) =
        withContext(Dispatchers.IO) {
            gymsDao.update(
                LocalGymFavouriteState(
                    id = gymId,
                    isFavourite = state
                )
            )
            return@withContext gymsDao.getAll()
        }

    suspend fun loadGyms() = withContext(Dispatchers.IO) {
        try {
            updateLocalDatabase()
        } catch (e: Exception) {
            if (gymsDao.getAll().isEmpty())
                throw Exception("Something is wrong ,no Data was found ,try connection to internet")
        }
    }

    suspend fun getGyms() = withContext(Dispatchers.IO) {
        return@withContext gymsDao.getAll().map {
            GymData(
                id = it.id,
                name = it.name,
                place = it.place,
                isOpen = it.isOpen,
               isFavourite =it.isFavourite
            )
        }
    }

    private suspend fun updateLocalDatabase() {
        val gyms = apiService.getGyms()
        val favouriteGymsList = gymsDao.getFavouriteGyms()

        gymsDao.addAll(
            gyms.map {
            LocalGymData(
                id = it.id,
                name = it.name,
                place = it.place,
                isOpen = it.isOpen,
            )

        })

        gymsDao.updateAll(
            favouriteGymsList.map { LocalGymFavouriteState(it.id, true) }
        )
    }
}