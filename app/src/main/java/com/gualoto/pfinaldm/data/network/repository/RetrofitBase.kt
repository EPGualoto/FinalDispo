package com.gualoto.pfinaldm.data.network.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBase {

    private const val JIKAN_URL = "https://api.jikan.moe/v4/"

    fun returnBaseRetrofitAnime(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(JIKAN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

