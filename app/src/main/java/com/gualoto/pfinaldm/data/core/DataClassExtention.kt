package com.gualoto.pfinaldm.data.core

import com.gualoto.pfinaldm.data.network.entities.topanime.Data
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG

//Así construyo una función de extención
fun com.gualoto.pfinaldm.data.network.entities.fullanime.FullInfoAnime.getFullInfoAnimeLG() = FullInfoAnimeLG(
    this.data.mal_id,
    this.data.title_english,
    this.data.images.jpg.small_image_url,
    this.data.images.jpg.large_image_url,
    this.data.synopsis,
    this.data.duration,
    this.data.type
)
fun Data.getFullInfoAnimeLG() = FullInfoAnimeLG(
    this.mal_id,
    this.title_english,
    this.images.jpg.small_image_url,
    this.images.jpg.large_image_url,
    this.synopsis,
    this.duration,
    this.type
)
