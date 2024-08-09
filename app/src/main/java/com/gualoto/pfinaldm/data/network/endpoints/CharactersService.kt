package com.gualoto.pfinaldm.data.network.endpoints

import com.gualoto.pfinaldm.data.network.entities.characters.CharactersApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersService {

    @GET("characters")
    fun getCharacters(@Query("q") query: String, @Query("page") page: Int): Call<CharactersApi>
}