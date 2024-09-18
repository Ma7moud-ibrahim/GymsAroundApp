package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsListRepository
import javax.inject.Inject

class GetSortedGemsUseCase @Inject constructor(
    private val repository: GymsListRepository
) {
    suspend operator fun invoke(): List<GymData> {
        return repository.getGyms().sortedBy { it.name }
    }
}