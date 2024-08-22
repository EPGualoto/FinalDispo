package com.gualoto.pfinaldm.data.network.endpoints

import com.gualoto.pfinaldm.data.network.entities.news.NewsAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {
    @GET("anime/{id}/news")
    fun getNews(@Path("id") animeId: Int): Call<NewsAPI>
}
