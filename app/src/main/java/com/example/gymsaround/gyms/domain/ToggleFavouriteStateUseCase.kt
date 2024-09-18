package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsListRepository
import javax.inject.Inject

class ToggleFavouriteStateUseCase @Inject constructor(
    private val gymsListRepository: GymsListRepository,
    private val getSortedGemsUseCase: GetSortedGemsUseCase
) {
    suspend operator fun invoke(gymId: Int, state: Boolean):List<GymData> {
        gymsListRepository.toggleFavouriteGym(gymId, state.not())
        return getSortedGemsUseCase()
    }


}