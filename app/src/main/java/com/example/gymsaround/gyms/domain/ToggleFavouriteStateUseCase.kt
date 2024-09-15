package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsRepository

class ToggleFavouriteStateUseCase {
    private var repository = GymsRepository()
    private var getSortedGemsUseCase = GetSortedGemsUseCase()
    suspend operator fun invoke(gymId: Int, state: Boolean):List<GymData> {
        repository.toggleFavouriteGym(gymId, state.not())
        return getSortedGemsUseCase()
    }


}