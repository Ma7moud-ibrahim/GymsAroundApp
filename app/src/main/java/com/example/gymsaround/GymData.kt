package com.example.gymsaround

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//val listGymsData = listOf<GymData>(
//    GymData( 1,"UpTown Gym",  "20 El-ÄÜ, AI Giza Governorate 3753612 Nit Giza Governorate 3754204, Egypt"),
//    GymData(2,"Gold's Gym",  "61 Syria, Hit  AI Giza Governorate 3753612 Giza Governorate 3752302, Egypt"),
//    GymData(3,"Hammer Gym", "7 Sphinx Square, AI Giza Governorate 3753612 AI Giza Governorate 3753612, Egypt"),
//    GymData(4,"I-Energy Gym", "2 At AI Giza Governorate 3753612 Giza Governorate 3753620, Egypt"),
//    GymData( 5,"H20 Gym & Spa", "32 AI Giza Governorate 3753612 Anas Ibn Malek, Mit Giza Governorate 3752212, Egypt"),
//    )

@Entity (tableName = "gyms")
data class GymData(
    @PrimaryKey
    @ColumnInfo("gym_id")
    val id: Int,
    @ColumnInfo("gym_name")
    @SerializedName("gym_name")
    val name: String,
    @ColumnInfo("gym_location")
    @SerializedName("gym_location")
    val place: String,
    @SerializedName("is_open")
    val isOpen:Boolean,
    @ColumnInfo("is_favourite")
    val isFavourite: Boolean = false
)