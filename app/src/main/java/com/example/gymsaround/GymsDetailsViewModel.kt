package com.example.gymsaround

import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsDetailsViewModel ():ViewModel(){
    private  var apiService: GymsAPIService
    init {
        val retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(
                "https://gyms-cario-default-rtdb.firebaseio.com/"
            ).build()
        apiService = retrofit.create(GymsAPIService::class.java)
    }
}