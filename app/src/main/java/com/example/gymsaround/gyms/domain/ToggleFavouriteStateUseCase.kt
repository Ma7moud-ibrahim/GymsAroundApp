package com.example.gymsaround.gyms.domain

import com.example.gymsaround.gyms.data.GymsListRepository

class ToggleFavouriteStateUseCase {
    private var repository = GymsListRepository()
    private var getSortedGemsUseCase = GetSortedGemsUseCase()
    suspend operator fun invoke(gymId: Int, state: Boolean):List<GymData> {
        repository.toggleFavouriteGym(gymId, state.not())
        return getSortedGemsUseCase()
    }


}