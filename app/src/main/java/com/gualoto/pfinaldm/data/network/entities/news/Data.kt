package com.gualoto.pfinaldm.data.network.entities.news

data class Data(
    val author_url: String,
    val author_username: String,
    val comments: Int,
    val date: String,
    val excerpt: String,
    val forum_url: String,
    val images: Images,
    val mal_id: Int,
    val title: String,
    val url: String
)