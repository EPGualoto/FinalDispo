package com.gualoto.pfinaldm.ui.core

import com.gualoto.pfinaldm.data.network.entities.anime.season.now.Genre
import com.gualoto.pfinaldm.data.network.entities.anime.season.now.Trailer

data class SeasonAnimeUI(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val score: Double,
    val rank: Int,
    val popularity: Int,
    val members: Int,
    val favorites: Int,
    val genres: List<Genre>,
    val episodes: Int,
    val synopsis: String,
    val trailer: Trailer

)


