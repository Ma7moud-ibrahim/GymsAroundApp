package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsListRepository
import javax.inject.Inject

class GetInitialGymsUseCase @Inject constructor(
    private val repository: GymsListRepository,
    private val getSortedGemsUseCase: GetSortedGemsUseCase
) {
    suspend operator fun invoke(): List<GymData> {
        repository.loadGyms()
        return getSortedGemsUseCase()
    }
}
