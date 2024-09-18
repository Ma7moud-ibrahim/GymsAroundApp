package com.example.gymsaround.gyms.data

import com.example.gymsaround.gyms.data.local.GymsDao
import com.example.gymsaround.gyms.data.local.LocalGymData
import com.example.gymsaround.gyms.data.local.LocalGymFavouriteState
import com.example.gymsaround.gyms.data.remote.GymsAPIService
import com.example.gymsaround.gyms.domain.GymData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GymsListRepository @Inject constructor(
    private val apiService: GymsAPIService,
    private val gymsDao: GymsDao

) {
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