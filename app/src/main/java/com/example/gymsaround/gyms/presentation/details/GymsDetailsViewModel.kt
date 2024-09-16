package com.example.gymsaround.gyms.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsaround.GymsApplication
import com.example.gymsaround.gyms.data.local.GymsDatabase
import com.example.gymsaround.gyms.data.remote.GymsAPIService
import com.example.gymsaround.gyms.domain.GymData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsDetailsViewModel(
savedStateHandle: SavedStateHandle // Inject SavedStateHandle
) : ViewModel() {

    val state = mutableStateOf<GymData?>(null)
    private val apiService: GymsAPIService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://gyms-cario-default-rtdb.firebaseio.com/")
            .build()
        apiService = retrofit.create(GymsAPIService::class.java)

        // Get gym_id from navigation arguments
        val gymId: Int? = savedStateHandle["gym_id"]
        gymId?.let { id ->
            loadGymDetails(id) // Fetch gym details using the id
        }
    }

    private fun loadGymDetails(gymId: Int) {
        viewModelScope.launch {
            try {
                val response = apiService.getGym(gymId) // Fetch the gym data using API call
                val gym = response.values.firstOrNull() // Extract the first gym from the map

                if (gym != null) {
                    state.value = GymData(
                        id = gym.id,
                        name = gym.name,
                        place = gym.place,
                        isOpen = gym.isOpen,
                    )
                } else {
                    // Handle the case where no gym was found for the provided id
                    throw Exception("No gym found with id: $gymId")
                }
            } catch (e: Exception) {
                // Log or handle the exception appropriately
                throw e
            }
        }
    }

}
