package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsRepository

class GetInitialGymsUseCase {
    private var repository  = GymsRepository()
    private var getSortedGemsUseCase = GetSortedGemsUseCase()
    suspend operator fun invoke(): List<GymData> {
        repository.loadGyms()
        return getSortedGemsUseCase()
    }
}
