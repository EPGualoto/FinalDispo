package com.gualoto.pfinaldm.ui.core

import com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.Aired
import com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.Broadcast
import com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.Demographic
import com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.Genre
import com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.Licensor
import com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.Producer
import com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.Studio
import com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.Theme
import com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.Title
import com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.Trailer

data class SeasonUAnimeUI(
    val aired: Aired?,
    val airing: Boolean?,
    val approved: Boolean?,
    val background: String?,
    val broadcast: Broadcast?,
    val demographics: List<Demographic>?,
    val duration: String?,
    val episodes: Int?,
    val explicit_genres: List<Any>?,
    val favorites: Int?,
    val genres: List<Genre>?,
    val imageUrl: String?,
    val licensors: List<Licensor>?,
    val id: Int?,
    val members: Int?,
    val popularity: Int?,
    val producers: List<Producer>?,
    val rank: Int?,
    val rating: String?,
    val score: Double?,
    val scored_by: Int?,
    val season: String?,
    val source: String?,
    val status: String?,
    val studios: List<Studio>?,
    val synopsis: String?,
    val themes: List<Theme>?,
    val title: String?,
    val title_english: String?,
    val title_japanese: String?,
    val title_synonyms: List<String>?,
    val titles: List<Title>?,
    val trailer: Trailer?,
    val type: String?,
    val url: String?,
    val year: Int?
)