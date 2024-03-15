package com.juanjimenez.catapi.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FavoriteCatRequest(
    @Json(name = "image_id") val image_id: String,
    @Json(name = "sub_id") val sub_id: String
)