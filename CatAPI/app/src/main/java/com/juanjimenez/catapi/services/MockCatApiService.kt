package com.juanjimenez.catapi.services

import com.juanjimenez.catapi.models.*
import retrofit2.Response

class MockCatApiService : CatApiService {
    override suspend fun getRandomCat(): Response<List<CatImage>> {
        val mockData = listOf(CatImage(id = "1", url = "https://cdn2.thecatapi.com/images/1i3.jpg"))
        return Response.success(mockData)
    }

    override suspend fun getFavoriteCats(): Response<List<CatFavorite>> {
        val mockData = listOf(
            CatFavorite(id = 1, userId = "user1", imageId = "image1", subId = "sub1", createdAt = "2021-01-01T00:00:00Z", image = CatImage(id = "1", url = "https://example.com/cat.jpg"))
        )
        return Response.success(mockData)
    }

    override suspend fun createFavoriteCat(requestBody: FavoriteCatRequest): Response<CatFavoriteResponse> {
        val mockResponse = CatFavoriteResponse(message = "Success", id = 1)
        return Response.success(mockResponse)
    }

    override suspend fun deleteFavoriteCat(favouriteId: Int): Response<Void> {
        // Since Response<Void> can be tricky to mock due to its generic nature,
        // you might consider using a workaround like creating a Response with null body
        // or using a specific response type instead of Void for a more meaningful response.
        return Response.success(null)
    }
}

