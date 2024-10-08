package com.example.gymsaround.gyms.data.remote

import com.google.gson.annotations.SerializedName



data class RemoteGymData(
    val id: Int,
    @SerializedName("gym_name")
    val name: String,
    @SerializedName("gym_location")
    val place: String,
    @SerializedName("is_open")
    val isOpen:Boolean,
)