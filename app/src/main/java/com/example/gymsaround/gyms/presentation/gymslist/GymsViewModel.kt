package com.example.gymsaround.gyms.presentation.gymslist

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsaround.gyms.domain.GetInitialGymsUseCase
import com.example.gymsaround.gyms.domain.ToggleFavouriteStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

@HiltViewModel
class GymsViewModel @Inject constructor(
    private val getInitialGymsUseCase: GetInitialGymsUseCase,
    private val toggleFavouriteStateUseCase: ToggleFavouriteStateUseCase,
) : ViewModel() {

    private var _state by mutableStateOf(
        GymsScreenState(
            gyms = emptyList(),
            isLoading = true
        )
    )

    //State In type state
    val state: State<GymsScreenState>
        get() = derivedStateOf { _state }


    private val errorHandle = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _state = _state.copy(
            isLoading = false,
            error = throwable.message
        )
    }

    init {
        getGyms()
    }

    private fun getGyms() {
        viewModelScope.launch(errorHandle) {
            val receivedGyms = getInitialGymsUseCase()
            _state = _state.copy(
                gyms = receivedGyms,
                isLoading = false
            )
        }
    }

    fun toggleFavouriteState(gymId: Int, oldValue: Boolean) {

        val gyms = _state.gyms.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        val stateIconIsFavourite = gyms[itemIndex].isFavourite

        viewModelScope.launch {
            val updateGymsList = toggleFavouriteStateUseCase(gymId, stateIconIsFavourite)
            _state = _state.copy(
                gyms = updateGymsList,
            )
        }

    }
}
