package com.gualoto.pfinaldm.data.network.entities.busqueda.anime

data class SearchAnimeApi(
    val `data`: List<Data>,
    val pagination: Pagination
)