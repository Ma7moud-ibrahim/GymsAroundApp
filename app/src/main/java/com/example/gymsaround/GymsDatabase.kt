package com.example.gymsaround

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [GymData::class],
    version = 2,
    exportSchema = false
)
abstract class GymsDatabase : RoomDatabase() {

    abstract fun dao(): GymsDao

    companion object {
        @Volatile
        private var daoInstance: GymsDao? = null

        private fun buildDatabase(context: Context): GymsDatabase = Room.databaseBuilder(
            context.applicationContext,
            GymsDatabase::class.java,
            "gyms_database"
        ).fallbackToDestructiveMigration().build()

        fun getDaoInstance(context: Context): GymsDao {
            synchronized(this) {
                if (daoInstance == null) {
                    daoInstance = buildDatabase(context).dao()
                }
                return daoInstance as GymsDao
            }
        }
    }
}
