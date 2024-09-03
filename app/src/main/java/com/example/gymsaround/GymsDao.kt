package com.example.gymsaround

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface GymsDao {
    @Query("SELECT * FROM gyms")
    suspend fun getAll(): List<GymData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(gyms: List<GymData>)

    @Update(entity = GymData::class)
    suspend fun update(gymFavouriteState: GymFavouriteState)

    @Query("SELECT * FROM gyms WHERE is_favourite = 1")
    suspend fun getFavouriteGyms():List<GymData>

    @Update(entity = GymData::class)
    suspend fun updateAll(gymsState: List<GymFavouriteState>)

    @Query("SELECT * FROM gyms WHERE gym_id = :gymId")
    suspend fun getGym(gymId: Int): GymData?
}
