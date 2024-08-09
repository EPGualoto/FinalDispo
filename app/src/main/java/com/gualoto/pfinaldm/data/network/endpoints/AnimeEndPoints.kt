package com.gualoto.pfinaldm.data.network.endpoints

import com.gualoto.pfinaldm.data.network.entities.topanime.TopAnimes
import com.gualoto.pfinaldm.data.network.entities.topmanga.AnimeManga
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeEndPoints {
    @GET("anime/{id}/full") // Endpoint para obtener información completa de un anime
    suspend fun getAnimeFullInformation(@Path("id") id: Int): Response<com.gualoto.pfinaldm.data.network.entities.fullanime.FullInfoAnime>

    @GET("top/anime") // Endpoint para obtener los animes top
    suspend fun getAllTopAnimes(
        @Query("page") page: Int = 1
    ): Response<TopAnimes>

    @GET("top/manga")
    suspend fun getAllTopMangas(
        @Query("page") page: Int = 1
    ): Response<AnimeManga>

}