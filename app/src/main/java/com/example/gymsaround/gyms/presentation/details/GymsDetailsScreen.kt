package com.example.gymsaround.gyms.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymsaround.gyms.presentation.component.DefaultIcons
import com.example.gymsaround.gyms.presentation.component.GymDetails
@Composable
fun GymsDetailsScreen(
    gymId: Int
) {
    val vm: GymsDetailsViewModel = viewModel() // Use ViewModel
    val item = vm.state.value

    item?.let {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            DefaultIcons(
                icon = Icons.Filled.Place,
                modifier = Modifier
                    .padding(bottom = 30.dp, top = 50.dp)
                    .size(65.dp),
                contentDescription = "Location Icon"
            )
            GymDetails(
                dataGyms = it,
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            Text(
                text = if (item.isOpen) "Gym is open" else "Gym is Closed",
                color = if (item.isOpen) Color.Green else Color.Red
            )
        }
    }
}
