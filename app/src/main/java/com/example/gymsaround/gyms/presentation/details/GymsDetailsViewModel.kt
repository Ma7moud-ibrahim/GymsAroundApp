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
//    private val gymsDao = GymsDatabase.getDaoInstance(GymsApplication.getApplicationContext())
    private val apiService: GymsAPIService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://gyms-cario-default-rtdb.firebaseio.com/")
            .build()
        apiService = retrofit.create(GymsAPIService::class.java)

        val gymId = savedStateHandle.get<Int>("gym_id") ?: 0
        getGym(gymId)
    }

    private fun getGym(id: Int) {
        viewModelScope.launch {
            val gym = getGymsFromRemoteDB(id)
            state.value = gym
        }
    }

    private suspend fun getGymsFromRemoteDB(id: Int) = withContext(Dispatchers.IO) {
        apiService.getGym(id).values.first().let {
            GymData(
                id = it.id ,
                name = it.name ,
                place = it.place,
                isOpen = it.isOpen
            )
        }
    }
}
