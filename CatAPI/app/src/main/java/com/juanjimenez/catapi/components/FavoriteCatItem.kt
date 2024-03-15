package com.juanjimenez.catapi.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.juanjimenez.catapi.models.CatFavorite
import com.juanjimenez.catapi.ui.FavoriteCats.FavoriteCatsViewModel

@Composable
fun FavoriteCatItem(favorite: CatFavorite, viewModel: FavoriteCatsViewModel) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .height(70.dp)
            .background(Color.White.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        favorite.image?.url?.let { imageUrl ->
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Text(
            "Favorite ID: ${favorite.id}",
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )

        Button(
            onClick = {
                viewModel.deleteFavoriteConfirmation(favorite.id)
            },
            modifier = Modifier.weight(1f)
        ) {
            Text("Remove")
        }
    }
}