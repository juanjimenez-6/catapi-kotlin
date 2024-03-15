package com.juanjimenez.catapi.services

import com.juanjimenez.catapi.models.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CatApiService {
    @GET("images/search")
    suspend fun getRandomCat(): Response<List<CatImage>>

    @GET("favourites")
    suspend fun getFavoriteCats(): Response<List<CatFavorite>>

    @POST("favourites")
    suspend fun createFavoriteCat(
        @Body requestBody: FavoriteCatRequest
    ): Response<CatFavoriteResponse>

    @DELETE("favourites/{favourite_id}")
    suspend fun deleteFavoriteCat(
        @Path("favourite_id") favouriteId: Int
    ): Response<Void>
}
