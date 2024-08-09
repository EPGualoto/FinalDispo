package com.gualoto.pfinaldm.data.network.endpoints

import com.gualoto.pfinaldm.data.network.entities.animet.JikanApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JikanService {
    @GET("anime")
    fun getAnime(@Query("q") query: String, @Query("page") page: Int): Call<JikanApi>
}