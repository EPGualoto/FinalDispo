package com.gualoto.pfinaldm.ui.core


import com.gualoto.pfinaldm.data.network.entities.news.Data

fun Data.toNewsUI() = NewsUI(
        id =this.mal_id,
        title = this.title,
        date = this.date,
        excerpt = this.excerpt,
        imageUrl = this.images.jpg.image_url,
        url = this.url,
        author_username = this.author_username,
        comments= this.comments
)

data class NewsUI(
        val id: Int,
        val title: String,
        val date: String,
        val excerpt: String,
        val imageUrl: String,
        val url: String,
        val comments: Int, // Asegúrate de tener esta propiedad si es necesario
        val author_username: String,
)

fun com.gualoto.pfinaldm.data.network.entities.anime.topAnime.Data.toTopAnimeUI() = TopAnimeUI(
        id = this.mal_id,
        title = this.title,
        imageUrl = this.images.jpg.image_url,
        score = this.score,
        rank = this.rank,
        popularity = this.popularity,
        members = this.members,
        favorites = this.favorites,
        genres = this.genres,
        episodes = this.episodes,
        synopsis = this.synopsis
)

fun com.gualoto.pfinaldm.data.network.entities.anime.season.now.Data.toSeasonAnimeUI() = SeasonAnimeUI(
        id = this.mal_id,
        title = this.title,
        imageUrl = this.images.jpg.image_url,
        score = this.score,
        rank = this.rank,
        popularity = this.popularity,
        members = this.members,
        favorites = this.favorites,
        genres = this.genres,
        episodes = this.episodes,
        synopsis = this.synopsis,
        trailer = this.trailer
)


fun com.gualoto.pfinaldm.data.network.entities.anime.season.upcoming.Data.toSeasonUAnimeUI() = SeasonUAnimeUI(
        id = this.mal_id,
        title = this.title,
        imageUrl = this.images.jpg.image_url,
        score = this.score,
        rank = this.rank,
        popularity = this.popularity,
        members = this.members,
        favorites = this.favorites,
        genres = this.genres,
        episodes = this.episodes,
        synopsis = this.synopsis,
        trailer = this.trailer
)

