package com.example.gymsaround

import androidx.annotation.ContentView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymsaround.ui.theme.Purple200
import com.example.gymsaround.ui.theme.Purple80


@Preview(showBackground = true)
@Composable
fun GymsScreen() {
    GymItem()
}

@Composable
fun GymItem() {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier.padding(8.dp)
    ) {
        Row {
            GymIcon(Icons.Default.Place, Modifier.weight(0.15F))
            GymDetails(
                "UpTownGym",
                "20 El-Gihad,Mit Akapa, Aquiza,Giza Governorate 37542C4, Egypt",
                Modifier.weight(0.70F)
            )
        }
    }
}

@Composable
fun GymDetails(title: String, supTitle: String, modifier: Modifier) {
    Column (
        modifier = modifier
    ){
        Text(text = title, style = MaterialTheme.typography.titleLarge, color = Purple200)
        Text(text = supTitle, style = MaterialTheme.typography.bodySmall, modifier = Modifier.alpha(0.9f))
    }
}

@Composable
fun GymIcon(icon : ImageVector, modifier: Modifier) {
    Image(
        imageVector = icon,
        contentDescription = "Icon Gyms",
        modifier = modifier.padding(10.dp),
        colorFilter = ColorFilter.tint( Color.DarkGray)
    )

}
