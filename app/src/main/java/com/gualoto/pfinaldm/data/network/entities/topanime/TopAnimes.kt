package com.gualoto.pfinaldm.data.network.entities.topanime

data class TopAnimes(
    val `data`: List<Data> = listOf(),
    val pagination: Pagination? = null
)