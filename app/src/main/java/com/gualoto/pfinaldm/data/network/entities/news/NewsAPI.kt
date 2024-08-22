package com.gualoto.pfinaldm.data.network.entities.news

data class NewsAPI(
    val `data`: List<Data>,
    val pagination: Pagination
)