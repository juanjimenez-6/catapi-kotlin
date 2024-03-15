import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juanjimenez.catapi.models.CatFavoriteResponse
import com.juanjimenez.catapi.models.CatImage
import com.juanjimenez.catapi.models.FavoriteCatRequest
import com.juanjimenez.catapi.services.CatApiService
import com.juanjimenez.catapi.ui.RandomCat.RandomCatViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Response

@ExperimentalCoroutinesApi
class RandomCatViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = TestCoroutineRule()

    private lateinit var viewModel: RandomCatViewModel
    private lateinit var catApiService: CatApiService

    @Before
    fun setUp() {
        catApiService = Mockito.mock(CatApiService::class.java)
        viewModel = RandomCatViewModel(catApiService)
    }

    @Test
    fun `fetch random cat image successfully`() = coroutinesRule.runBlockingTest {
        val mockCatImage = CatImage("id", "url")
        `when`(catApiService.getRandomCat()).thenReturn(Response.success(listOf(mockCatImage)))

        viewModel.fetchRandomCatImage()

        assert(viewModel.isLoading.value == false)
        assert(viewModel.catImage.value == mockCatImage)
        assert(viewModel.success.value == true)
        assert(viewModel.error.value == false)
        assert(viewModel.errorMessage.value == null)
    }

    @Test
    fun `fetch random cat image fails`() = coroutinesRule.runBlockingTest {
        val errorMessage = "Network Error"
        val runtimeException = RuntimeException(errorMessage)
        `when`(catApiService.getRandomCat()).thenAnswer { throw runtimeException }


        viewModel.fetchRandomCatImage()

        assert(viewModel.isLoading.value == false)
        assert(viewModel.catImage.value == null)
        assert(viewModel.success.value == false)
        assert(viewModel.error.value == true)
        assert(viewModel.errorMessage.value == errorMessage)
    }

    @Test
    fun `create favorite success`() = coroutinesRule.runBlockingTest {
        val catImageId = "1"
        val subId = "user123"
        val response = CatFavoriteResponse("Success", 1)
        val request = FavoriteCatRequest(catImageId, subId)

        `when`(catApiService.createFavoriteCat(request))
            .thenReturn(Response.success(response))

        viewModel.createFavorite(catImageId, subId)

        assert(viewModel.success.value == true)
        assert(viewModel.error.value == false)
        assert(viewModel.favorite.value == response)
    }

    @Test
    fun `create favorite failure`() = coroutinesRule.runBlockingTest {
        val catImageId = "1"
        val subId = "user123"
        val errorMessage = "Error creating favorite"
        val exception = RuntimeException(errorMessage)
        val request = FavoriteCatRequest(catImageId, subId)

        `when`(catApiService.createFavoriteCat(request))
            .thenThrow(exception)

        viewModel.createFavorite(catImageId, subId)

        assert(viewModel.success.value == false)
        assert(viewModel.error.value == true)
        assert(viewModel.errorMessage.value == errorMessage)
    }

    @Test
    fun `delete favorite success`() = coroutinesRule.runBlockingTest {
        val favoriteId = 1

        `when`(catApiService.deleteFavoriteCat(favoriteId))
            .thenReturn(Response.success(null))

        viewModel.deleteFavorite(favoriteId)

        assert(viewModel.success.value == true)
        assert(viewModel.error.value == false)
    }

    @Test
    fun `delete favorite failure`() = coroutinesRule.runBlockingTest {
        val favoriteId = 1
        val errorMessage = "Error deleting favorite"
        val exception = RuntimeException(errorMessage)

        `when`(catApiService.deleteFavoriteCat(favoriteId))
            .thenThrow(exception)

        viewModel.deleteFavorite(favoriteId)

        assert(viewModel.success.value == false)
        assert(viewModel.error.value == true)
    }
}
