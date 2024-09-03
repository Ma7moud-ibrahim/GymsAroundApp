package com.example.gymsaround

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GymsDetailsViewModel(
    savedStateHandle: SavedStateHandle // Inject SavedStateHandle
) : ViewModel() {

    val state = mutableStateOf<GymData?>(null)
    private val gymsDao = GymsDatabase.getDaoInstance(GymsApplication.getApplicationContext())
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
            var gym = gymsDao.getGym(id)
            if (gym == null) {
                gym = getGymsFromRemoteDB(id)
                gym?.let { gymsDao.addAll(listOf(it)) }
            }
            state.value = gym
        }
    }

    private suspend fun getGymsFromRemoteDB(id: Int): GymData? = withContext(Dispatchers.IO) {
        apiService.getGym(id).values.firstOrNull()
    }
}
