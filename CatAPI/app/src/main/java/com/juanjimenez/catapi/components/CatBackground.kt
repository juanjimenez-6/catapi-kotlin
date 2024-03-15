package com.juanjimenez.catapi.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juanjimenez.catapi.R

@Composable
fun CatBackground(
    anyComposable: @Composable () -> Unit
) {
    Box(modifier = Modifier
        .padding(0.dp)
        .fillMaxSize()
        .background(Color.Gray)) {
        Image(
            modifier = Modifier.fillMaxSize().blur(radiusX = 10.dp, radiusY = 10.dp) ,
            painter = painterResource(R.drawable.cats_background),
            contentDescription = "background_image",
            contentScale = ContentScale.Crop,

        )
        anyComposable()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCatBackground() {
    MaterialTheme {
        CatBackground(anyComposable = { Row() { Text(text = "Empty View") }})
    }
}