package com.juanjimenez.catapi.services

import com.juanjimenez.catapi.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.thecatapi.com/v1/"

    private val contentTypeInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        if (originalRequest.method in listOf("POST", "PUT")) {
            requestBuilder.addHeader("Content-Type", "application/json")
        }

        chain.proceed(requestBuilder.build())
    }

    private val apiKeyInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("x-api-key", "live_UEOvpeIIRe8IHrUDObRBspbDXLlQ0KvhKP6FXq5JXD0lyEzOXfX471WEX4aTPz8m")
            .build()
        chain.proceed(newRequest)
    }

    val moshi = Moshi.Builder().build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(OkHttpClient.Builder().addInterceptor(apiKeyInterceptor).addInterceptor(contentTypeInterceptor).apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(loggingInterceptor)
            }
        }.build())
        .build()
}

object NetworkClient {
    val catApiService: CatApiService by lazy {
        RetrofitClient.retrofit.create(CatApiService::class.java)
    }
}