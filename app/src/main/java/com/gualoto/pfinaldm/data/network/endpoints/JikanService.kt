package com.gualoto.pfinaldm.data.network.endpoints


import com.gualoto.pfinaldm.data.network.entities.busqueda.anime.SearchAnimeApi
import com.gualoto.pfinaldm.data.network.entities.jikan.JikanApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JikanService {
    @GET("anime")
    fun getAnime(@Query("q") query: String, @Query("page") page: Int): Call<JikanApi>

    @GET("anime")
    fun searchAnimeType(@Query("type") type: String, @Query("page") page: Int): Call<SearchAnimeApi>

    @GET("anime")
    fun searchAllAnimeTypes(
        @Query("page") page: Int
    ): Call<SearchAnimeApi>

}