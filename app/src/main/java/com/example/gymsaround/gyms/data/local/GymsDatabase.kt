package com.example.gymsaround.gyms.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalGymData::class],
    version = 3,
    exportSchema = false
)
abstract class GymsDatabase : RoomDatabase() {

    abstract fun dao(): GymsDao
}
