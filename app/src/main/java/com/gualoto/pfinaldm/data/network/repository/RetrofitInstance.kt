package com.gualoto.pfinaldm.data.network.repository

import com.gualoto.pfinaldm.data.network.entities.anime.season.now.SeasonApi
import com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.UpcomingApi
import com.gualoto.pfinaldm.data.network.entities.anime.topAnime.TopAnimeApi
import com.gualoto.pfinaldm.data.network.entities.clubs.fullClub.ClubApi
import com.gualoto.pfinaldm.data.network.entities.clubs.members.MemberApi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApiService {
    @GET("top/anime")
    suspend fun getTopAnimes(): Response<TopAnimeApi>

    @GET("seasons/now")
    suspend fun getSeasonNowAnimes(): Response<SeasonApi>

    @GET("seasons/upcoming")
    suspend fun getSeasonUpcomingAnimes(): Response<UpcomingApi>

    @GET("clubs")
    suspend fun getClubs(
        @Query("q") query: String? = null,
        @Query("page") page: Int
    ): Response<ClubApi>

    @GET("clubs/{id}/members")
    suspend fun getClubMembers(@Path("id") clubId: Int): Response<MemberApi>
}

object RetrofitInstance {
    val api: JikanApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JikanApiService::class.java)
    }
}
