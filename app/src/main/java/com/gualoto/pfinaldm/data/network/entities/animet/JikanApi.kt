package com.gualoto.pfinaldm.data.network.entities.animet

import com.gualoto.pfinaldm.data.network.entities.jikan.Data
import com.gualoto.pfinaldm.data.network.entities.jikan.Pagination

data class JikanApi(
    val `data`: List<Data>,
    val pagination: Pagination
)