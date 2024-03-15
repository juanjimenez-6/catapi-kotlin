package com.juanjimenez.catapi.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatFavorite(
    val id: Int,
    @Json(name = "user_id") val userId: String,
    @Json(name = "image_id") val imageId: String,
    @Json(name = "sub_id") val subId: String,
    @Json(name = "created_at") val createdAt: String,
    val image: CatImage?
)
