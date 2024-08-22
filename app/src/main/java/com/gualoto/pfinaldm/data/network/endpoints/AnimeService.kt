package com.gualoto.pfinaldm.data.network.endpoints

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeService {
    @GET("anime")
    fun getAnimeList(@Query("page") page: Int): Call<AnimeListResponse>
}

data class AnimeListResponse(
    val data: List<Anime>
)

data class Anime(
    val mal_id: Int,
    val title: String,
    // Añade otros campos que necesites
)
