package com.juanjimenez.catapi.ui.RandomCat

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
import com.juanjimenez.catapi.services.MockCatApiService

@Composable
fun RandomCatViewCompose(randomCatViewModel: RandomCatViewModel = viewModel()) {
    val catImage by randomCatViewModel.catImage.observeAsState()
    val isLoading by randomCatViewModel.isLoading.observeAsState()
    val favorite by randomCatViewModel.favorite.observeAsState()
    val errorMessage by randomCatViewModel.errorMessage.observeAsState()
    val userId = "juanjimenez001"

    CatBackground(anyComposable = {
            Box(modifier = Modifier
                .padding(0.dp)
                .fillMaxSize()) {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .background(
                                Color.White.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (catImage != null) {
                            CatImageView(url = catImage!!.url)
                        } else if (isLoading == true) {
                            //CircularProgressIndicator("Fetching Cat Data...")
                            Text("Fetching Cat...")
                        }
                    }

                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .background(
                                Color.White.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                            Button(onClick = { randomCatViewModel.fetchRandomCatImage() }) {
                                Text("Fetch Another Cat")
                            }
                            if (favorite != null) {
                                Button(onClick = { randomCatViewModel.deleteFavorite(favoriteId = favorite!!.id) },
                                    enabled = catImage != null) {
                                    Text("Remove From Favorites")
                                }
                            } else if (catImage != null) {
                                Button(onClick = { catImage?.id?.let { randomCatViewModel.createFavorite(catImage!!.id, userId) } },
                                    enabled = catImage != null) {
                                    Text("Add Cat To Favorites")
                                }
                            }
                        }
                    }
                    when {
                        isLoading == true -> {
                            CircularProgressIndicator()
                        }
                        errorMessage != null -> {
                            AlertDialog(
                                onDismissRequest = {
                                    randomCatViewModel.resetErrorMessageState()
                                },
                                title = {
                                    Text(text = "An Error Occurred")
                                },
                                text = {
                                    Text(errorMessage!!)
                                },

                                confirmButton = {
                                    Button(
                                        onClick = {
                                            randomCatViewModel.resetErrorMessageState()
                                        }) {
                                        Text("Ok")
                                    }
                                }
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
fun PreviewRandomCatComposeView() {
    val mockService = MockCatApiService()
    val mockViewModel = RandomCatViewModel(catApiService = mockService)

    MaterialTheme {
        RandomCatViewCompose(randomCatViewModel = mockViewModel)
    }
}
