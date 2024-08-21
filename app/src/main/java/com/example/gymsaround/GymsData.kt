package com.example.gymsaround

import com.google.gson.annotations.SerializedName

val listGymsData = listOf<GymsData>(
    GymsData( 1,"UpTown Gym",  "20 El-ÄÜ, AI Giza Governorate 3753612 Nit Giza Governorate 3754204, Egypt"),
    GymsData(2,"Gold's Gym",  "61 Syria, Hit  AI Giza Governorate 3753612 Giza Governorate 3752302, Egypt"),
    GymsData(3,"Hammer Gym", "7 Sphinx Square, AI Giza Governorate 3753612 AI Giza Governorate 3753612, Egypt"),
    GymsData(4,"I-Energy Gym", "2 At AI Giza Governorate 3753612 Giza Governorate 3753620, Egypt"),
    GymsData( 5,"H20 Gym & Spa", "32 AI Giza Governorate 3753612 Anas Ibn Malek, Mit Giza Governorate 3752212, Egypt"),
    )

data class GymsData(
    val id: Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val place: String,
    var isFavourite: Boolean = false
)