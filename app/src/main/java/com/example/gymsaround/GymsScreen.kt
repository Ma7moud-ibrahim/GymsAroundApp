package com.example.gymsaround

import GymsAppBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymsaround.ui.theme.GymsAroundTheme
import com.example.gymsaround.ui.theme.Purple200


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GymsScreen() {
    val vm: GymsViewModel = viewModel()
    vm.getGyms()
    Column {
        GymsAppBar(title = R.string.general_screen, showBackButton = false) {
        }
        LazyColumn {
            items(vm.state) { gym ->
                GymItem(gym) {
                    vm.toggleFavouriteState(it)

                }
            }
        }
    }
}


@Composable
fun GymItem(dataGyms: GymsData,onClick: (Int) -> Unit) {
    val icon = if (dataGyms.isFavourite){
        Icons.Filled.Favorite
    }else {
        Icons.Filled.FavoriteBorder
    }
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            DefaultIcons(Icons.Default.Place, Modifier.weight(0.15F),"Place Icon")
            GymDetails(dataGyms ,Modifier.weight(0.70F))
            DefaultIcons(icon,Modifier.weight(0.15f),"Favourite Icon"){
                onClick(dataGyms.id)
            }
        }
    }
}
@Composable
fun DefaultIcons(
    icon: ImageVector,
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit = {}
) {
    Image(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(10.dp)
            .clickable {
                onClick()
            } ,
        colorFilter = ColorFilter.tint( Color.DarkGray)
    )
}

@Composable
fun GymDetails(dataGyms: GymsData, modifier: Modifier) {
    Column (
        modifier = modifier
    ) {
        Text(text = dataGyms.name, style = MaterialTheme.typography.titleLarge, color = Purple200)
        Text(
            text = dataGyms.place,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.9f), maxLines = 2
        )
    }
}

@Preview(showBackground = true)
@Composable
fun _GymScreenPreview(){
    GymsAroundTheme {
        GymsScreen()
    }
}