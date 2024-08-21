package com.example.gymsaround

import retrofit2.Call
import retrofit2.http.GET

interface GymsAPIService {
    @GET("gyms.json")
    fun getGyms():Call<List<GymsData>>
}