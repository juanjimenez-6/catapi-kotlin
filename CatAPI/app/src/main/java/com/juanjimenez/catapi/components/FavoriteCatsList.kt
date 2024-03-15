package com.juanjimenez.catapi.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.juanjimenez.catapi.models.CatFavorite
import com.juanjimenez.catapi.ui.FavoriteCats.FavoriteCatsViewModel

@Composable
fun FavoriteCatsList(favorites: List<CatFavorite>, viewModel: FavoriteCatsViewModel) {
    LazyColumn {
        items(favorites.count()) { favorite ->
            FavoriteCatItem(favorite = favorites[favorite], viewModel = viewModel)
        }
    }
}