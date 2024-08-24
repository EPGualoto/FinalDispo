package com.gualoto.pfinaldm.data.network.repository
import com.gualoto.pfinaldm.data.network.endpoints.JikanService
import com.gualoto.pfinaldm.data.network.entities.busqueda.anime.SearchAnimeApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitClient {
    @GET("anime")
    fun searchAnimeType(
        @Query("type") type: String?,
        @Query("page") page: Int
    ): Call<SearchAnimeApi>

    @GET("anime")
    fun searchAllAnimeTypes(
        @Query("page") page: Int
    ): Call<SearchAnimeApi>

    companion object {
        val instance: RetrofitClient by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/v4/")  // Asegúrate de poner la URL correcta
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitClient::class.java)
        }
    }
}
