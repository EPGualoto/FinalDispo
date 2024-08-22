package com.gualoto.pfinaldm.data.network.repository

import com.gualoto.pfinaldm.data.network.endpoints.AnimeService
import com.gualoto.pfinaldm.data.network.endpoints.NewsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsClient {
    private const val BASE_URL = "https://api.jikan.moe/v4/"

    val instance: AnimeService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(AnimeService::class.java)
    }

    val newsService: NewsService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val newsService1: NewsService = retrofit.create(NewsService::class.java)
}
