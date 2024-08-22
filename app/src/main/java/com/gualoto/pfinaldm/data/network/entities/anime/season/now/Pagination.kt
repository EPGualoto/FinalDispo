package com.gualoto.pfinaldm.data.network.entities.anime.season.now

data class Pagination(
    val current_page: Int,
    val has_next_page: Boolean,
    val items: com.gualoto.pfinaldm.data.network.entities.anime.season.now.Items,
    val last_visible_page: Int
)