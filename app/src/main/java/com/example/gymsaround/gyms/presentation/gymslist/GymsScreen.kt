package com.example.gymsaround.gyms.presentation.gymslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.gymsaround.gyms.presentation.appbar.GymsAppBar
import com.example.gymsaround.R
import com.example.gymsaround.gyms.domain.GymData
import com.example.gymsaround.gyms.presentation.component.DefaultIcons
import com.example.gymsaround.gyms.presentation.component.GymDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymsScreen(
    state: GymsScreenState,
    onItemClick: (Int) -> Unit,
    onFavouriteClick: (id: Int,oldValue: Boolean) -> Unit,

) {

    Scaffold(
        topBar = {
            GymsAppBar(
                title = R.string.general_screen,
                showBackButton = false,
                color = colorResource(id = R.color.purple_200)
            ) {}
        }
    ) { paddingValues ->
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){
            LazyColumn(modifier = Modifier
                .padding(paddingValues)) {
                items(state.gyms) { gym ->
                    GymItem(
                        dataGyms = gym,
                        onClickFavouriteIcon = {id,oldValue ->
                            onFavouriteClick(id, oldValue) },
                        onItemClick = { id -> onItemClick(id) }
                    )
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            state.error?.let { Text(text = it, modifier = Modifier.align(Alignment.Center)) }
        }
    }
}

@Composable
fun GymItem(
    dataGyms: GymData,
    onClickFavouriteIcon: (Int,Boolean) -> Unit,
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
                onClickFavouriteIcon(dataGyms.id, dataGyms.isFavourite)
            }
        }
    }
}

