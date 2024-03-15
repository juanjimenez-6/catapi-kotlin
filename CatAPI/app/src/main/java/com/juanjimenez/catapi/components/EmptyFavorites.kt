package com.juanjimenez.catapi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EmptyFavorites() {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .height(30.dp)
            .background(Color.White.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("No Favorites")
    }
}
