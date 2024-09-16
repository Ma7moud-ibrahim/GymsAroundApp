//package com.example.gymsaround.gyms.data
//
//import com.example.gymsaround.GymsApplication
//import com.example.gymsaround.gyms.data.local.GymsDatabase
//import com.example.gymsaround.gyms.data.local.LocalGymFavouriteState
//import com.example.gymsaround.gyms.data.remote.GymsAPIService
//import com.example.gymsaround.gyms.domain.GymData
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class GymsDetailsRepository {
//    private var apiService = Retrofit.Builder()
//        .addConverterFactory(GsonConverterFactory.create())
//        .baseUrl("https://gyms-cario-default-rtdb.firebaseio.com/")
//        .build()
//        .create(GymsAPIService::class.java)
//
//    private var gymsDao = GymsDatabase.getDaoInstance(GymsApplication.getApplicationContext())
//
//    suspend fun getGymDetails(id: Int): GymData? {
//        return withContext(Dispatchers.IO) {
//            apiService.getGym(id).values.firstOrNull()?.let { remoteGym ->
//                GymData(
//                    id = remoteGym.id,
//                    name = remoteGym.name,
//                    place = remoteGym.place,
//                    isOpen = remoteGym.isOpen,
//                    isFavourite = gymsDao.getGym(id)?.isFavourite ?: false
//                )
//            }
//        }
//    }
//
//
//}
