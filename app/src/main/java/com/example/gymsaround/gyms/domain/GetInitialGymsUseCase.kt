package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsListRepository

class GetInitialGymsUseCase {
    private var repository  = GymsListRepository()
    private var getSortedGemsUseCase = GetSortedGemsUseCase()
    suspend operator fun invoke(): List<GymData> {
        repository.loadGyms()
        return getSortedGemsUseCase()
    }
}
