package com.example.gymsaround

import retrofit2.Call
import retrofit2.http.GET

interface GymsAPIService {
    @GET("gyms.json")
    suspend fun getGyms():List<GymsData>
}