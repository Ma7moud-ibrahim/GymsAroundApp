package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsRepository

class GetSortedGemsUseCase {
    private var repository = GymsRepository()
    suspend operator fun invoke(): List<GymData> {
        return repository.getGyms().sortedBy { it.name }
    }
}