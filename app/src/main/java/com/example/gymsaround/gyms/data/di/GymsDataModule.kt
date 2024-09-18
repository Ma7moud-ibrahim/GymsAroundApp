package com.example.gymsaround.gyms.data.di

import android.content.Context
import androidx.room.Room
import com.example.gymsaround.gyms.data.local.GymsDatabase
import com.example.gymsaround.gyms.data.remote.GymsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GymsDataModule {

    @Provides
    fun provideGymsApiService(retrofit: Retrofit): GymsAPIService {
        return retrofit.create(GymsAPIService::class.java)
    }

    @Provides
    fun provideGymsDao(database: GymsDatabase) = database.dao()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://gyms-cario-default-rtdb.firebaseio.com/")
            .build()
    }

    @Singleton
    @Provides
    fun ProvideRoomDatabase(
        @ApplicationContext context: Context
    ): GymsDatabase {
        return Room.databaseBuilder(
            context,
            GymsDatabase::class.java,
            "gyms_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}