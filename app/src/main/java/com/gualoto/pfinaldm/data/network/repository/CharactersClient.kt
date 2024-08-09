package com.gualoto.pfinaldm.data.network.repository

import com.gualoto.pfinaldm.data.network.endpoints.CharactersService

object CharactersClient {

    private const val BASE_URL = "https://api.jikan.moe/v4/"

    val instance: CharactersService by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
            .create(CharactersService::class.java)
    }


}