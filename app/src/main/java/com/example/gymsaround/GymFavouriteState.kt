package com.example.gymsaround

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class GymFavouriteState(
    @ColumnInfo("gym_id")
    val id: Int,
    @ColumnInfo("is_favourite")
    val isFavourite: Boolean = false
)