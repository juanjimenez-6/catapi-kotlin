import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juanjimenez.catapi.models.CatFavorite
import com.juanjimenez.catapi.models.CatImage
import com.juanjimenez.catapi.services.CatApiService
import com.juanjimenez.catapi.ui.FavoriteCats.FavoriteCatsViewModel
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Response

@ExperimentalCoroutinesApi
class FavoriteCatsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = TestCoroutineRule()

    private lateinit var viewModel: FavoriteCatsViewModel
    private lateinit var catApiService: CatApiService

    @Before
    fun setUp() {
        catApiService = Mockito.mock(CatApiService::class.java)
        viewModel = FavoriteCatsViewModel(catApiService)
    }

    @Test
    fun `fetch favorite cats success`() = coroutinesRule.runBlockingTest {
        val expectedCats = listOf(CatFavorite(id = 123, userId = "user123", imageId = "image123", subId = "sub123",createdAt = "date", image = CatImage("id", "url")))
        `when`(catApiService.getFavoriteCats()).thenReturn(Response.success(expectedCats))

        viewModel.fetchFavoriteCats()

        assertEquals(expectedCats, viewModel.favorites.value)
        assertTrue(viewModel.success.value!!)
        assertFalse(viewModel.error.value!!)
    }

    @Test
    fun `fetch favorite cats failure`() = coroutinesRule.runBlockingTest {
        val errorMessage = "Error fetching cats"
        val exception = RuntimeException(errorMessage)
        `when`(catApiService.getFavoriteCats()).thenThrow(exception)

        viewModel.fetchFavoriteCats()

        assertEquals(errorMessage, viewModel.errorMessage.value)
        assertFalse(viewModel.success.value!!)
        assertTrue(viewModel.error.value!!)
    }

    @Test
    fun `delete favorite success`() = coroutinesRule.runBlockingTest {
        val favoriteId = 1
        `when`(catApiService.deleteFavoriteCat(favoriteId)).thenReturn(Response.success(null))

        var result: Result<Void>? = null
        viewModel.deleteFavorite(favoriteId) {
            result = it
        }

        assertTrue(result is Result<Void>)
        assertTrue(viewModel.success.value!!)
        assertFalse(viewModel.error.value!!)
    }

    @Test
    fun `delete favorite failure`() = coroutinesRule.runBlockingTest {
        val favoriteId = 1
        val errorMessage = "Failed to delete"
        val exception = RuntimeException(errorMessage)

        `when`(catApiService.deleteFavoriteCat(favoriteId)).thenThrow(exception)

        var result: Result<Void>? = null
        viewModel.deleteFavorite(favoriteId) {
            result = it
        }

        assertTrue(result is Result<Void>)
        assertFalse(viewModel.success.value!!)
        assertTrue(viewModel.error.value!!)
    }
}
