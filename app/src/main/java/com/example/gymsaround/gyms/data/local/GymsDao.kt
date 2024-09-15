package com.example.gymsaround.gyms.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface GymsDao {
    @Query("SELECT * FROM gyms")
    suspend fun getAll(): List<LocalGymData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gyms: List<LocalGymData>)

    @Update(entity = LocalGymData::class)
    suspend fun update(localGymFavouriteState: LocalGymFavouriteState)

    @Query("SELECT * FROM gyms WHERE is_favourite = 1")
    suspend fun getFavouriteGyms():List<LocalGymData>

    @Update(entity = LocalGymData::class)
    suspend fun updateAll(gymsState: List<LocalGymFavouriteState>)

    @Query("SELECT * FROM gyms WHERE gym_id = :gymId")
    suspend fun getGym(gymId: Int): LocalGymData?
}
