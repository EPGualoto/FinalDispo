package com.gualoto.pfinaldm.data.network.repository

import com.gualoto.pfinaldm.data.network.endpoints.JikanService


object RetrofitClient {
    private const val BASE_URL = "https://api.jikan.moe/v4/"

    val instance: JikanService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(JikanService::class.java)
    }
}