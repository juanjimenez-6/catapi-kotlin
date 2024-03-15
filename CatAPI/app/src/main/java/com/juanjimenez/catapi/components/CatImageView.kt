import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.juanjimenez.catapi.R

@Composable
fun CatImageView(url: String) {
    Box(modifier = Modifier.size(width = 335.dp, height = 335.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentDescription = "Cat Image",
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = rememberAsyncImagePainter(model = R.drawable.placeholder),
            error = rememberAsyncImagePainter(model = R.drawable.image_load_failed)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCatImageView() {
    MaterialTheme {
        CatImageView(url = "https://cdn2.thecatapi.com/images/1i3.jpg")
    }
}
