package com.example.gymsaround.gyms.presentation.gymslist

import com.example.gymsaround.gyms.domain.GymData


data class GymsScreenState (
    val gyms :List<GymData>,
    val isLoading:Boolean,
    val error: String? = null
)