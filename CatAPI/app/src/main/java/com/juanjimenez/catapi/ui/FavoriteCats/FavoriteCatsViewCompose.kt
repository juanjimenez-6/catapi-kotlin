package com.juanjimenez.catapi.ui.FavoriteCats

import CatImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juanjimenez.catapi.components.CatBackground
import com.juanjimenez.catapi.components.EmptyFavorites
import com.juanjimenez.catapi.components.FavoriteCatsList
import com.juanjimenez.catapi.services.MockCatApiService

@Composable
fun FavoriteCatsViewCompose(favoriteCatsViewModel: FavoriteCatsViewModel = viewModel()) {
    val favorites by favoriteCatsViewModel.favorites.observeAsState()
    val isLoading by favoriteCatsViewModel.isLoading.observeAsState()
    val errorMessage by favoriteCatsViewModel.errorMessage.observeAsState()
    val favoriteToDelete by favoriteCatsViewModel.favoriteToDelete.observeAsState()
    val showConfirmDialog by favoriteCatsViewModel.showConfirmDialog.observeAsState()
    val userId = "juanjimenez001"

    CatBackground(anyComposable = {
        Box(modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                favorites?.let {
                    if (favorites!!.isEmpty()) {
                        EmptyFavorites()
                    } else {
                        FavoriteCatsList(favorites = favorites!!, viewModel = favoriteCatsViewModel)
                    }
                }
                when {
                    isLoading == true -> {
                        CircularProgressIndicator()
                    }
                    showConfirmDialog == true -> {
                        AlertDialog(
                            onDismissRequest = {
                                //randomCatViewModel.resetErrorMessageState()
                            },
                            title = {
                                Text(text = "Delete Favorite")
                            },
                            text = {
                                Text("Are you sure you want to delete this favorite?")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        favoriteToDelete?.let {
                                            favoriteCatsViewModel.deleteFavorite(it) { result ->
                                                if (result.isSuccess) {
                                                    favoriteCatsViewModel.fetchFavoriteCats()
                                                }
                                            }
                                        }
                                    }) {
                                    Text("Delete")
                                }
                            },
                            dismissButton  = {
                                Button(
                                    onClick = {
                                        favoriteCatsViewModel.deleteFavoriteCancel()
                                    }) {
                                    Text("Cancel")
                                }
                            },
                        )
                    }
                }
            }
        }
    }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteCatsComposeView() {
    val mockService = MockCatApiService()
    val mockViewModel = FavoriteCatsViewModel(catApiService = mockService)

    MaterialTheme {
        FavoriteCatsViewCompose(favoriteCatsViewModel = mockViewModel)
    }
}