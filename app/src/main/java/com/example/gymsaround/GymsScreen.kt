package com.example.gymsaround

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymsScreen(onItemClick: (Int) -> Unit) {
    val vm: GymsViewModel = viewModel()

    Scaffold(
        topBar = {
            GymsAppBar(
                title = R.string.general_screen,
                showBackButton = false,
                color = colorResource(id = R.color.purple_200)
            ) {}
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues).background(Color.LightGray)) {
            items(vm.state) { gym ->
                GymItem(
                    dataGyms = gym,
                    onClickFavouriteIcon = { vm.toggleFavouriteState(it) },
                    onItemClick = { id -> onItemClick(id) }
                )
            }
        }
    }
}

@Composable
fun GymItem(
    dataGyms: GymData,
    onClickFavouriteIcon: (Int) -> Unit,
    onItemClick: (Int) -> Unit
) {
    val icon = if (dataGyms.isFavourite) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick(dataGyms.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            DefaultIcons(Icons.Default.Place, Modifier.weight(0.15F), "Place Icon")
            GymDetails(dataGyms, Modifier.weight(0.70F))
            DefaultIcons(icon, Modifier.weight(0.15f), "Favourite Icon") {
                onClickFavouriteIcon(dataGyms.id)
            }
        }
    }
}

