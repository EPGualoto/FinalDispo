package com.gualoto.pfinaldm.data.network.entities.busqueda.manga

data class SearchMangaApi(
    val `data`: List<Data>,
    val pagination: Pagination
)