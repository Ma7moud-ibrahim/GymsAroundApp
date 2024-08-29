package com.example.gymsaround

import retrofit2.http.GET
import retrofit2.http.Query

interface GymsAPIService {
    @GET("gyms.json")
    suspend fun getGyms():List<GymData>

    @GET("/gyms.json?orderBy=\"id\"")
    suspend fun getGym( @Query("equalTo") id:Int):Map<String,GymData>

}