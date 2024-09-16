package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsListRepository

class GetSortedGemsUseCase {
    private var repository = GymsListRepository()
    suspend operator fun invoke(): List<GymData> {
        return repository.getGyms().sortedBy { it.name }
    }
}