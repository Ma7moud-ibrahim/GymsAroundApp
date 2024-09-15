package com.example.gymsaround.gyms.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gymsaround.gyms.domain.GymData
import com.example.gymsaround.ui.theme.Purple200

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
            .clickable { onClick() },
        colorFilter = ColorFilter.tint(Color.DarkGray)
    )
}


@Composable
fun GymDetails(
    dataGyms: GymData,
    modifier: Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = dataGyms.name,
            style = MaterialTheme.typography.titleLarge,
            color = Purple200
        )
        Text(
            text = dataGyms.place,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.9f),
            maxLines = 2,
            textAlign = TextAlign.Center
        )
    }
}