package com.example.gymsaround.gyms.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "gyms")
data class GymData(
    @PrimaryKey
    @ColumnInfo("gym_id")
    val id: Int,
    @ColumnInfo("gym_name")
    val name: String,
    @ColumnInfo("gym_location")
    val place: String,
    val isOpen:Boolean,
    @ColumnInfo("is_favourite")
    val isFavourite: Boolean = false
)