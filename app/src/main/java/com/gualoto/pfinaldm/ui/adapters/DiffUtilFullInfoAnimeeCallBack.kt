package com.gualoto.pfinaldm.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG

object DiffUtilFullInfoAnimeCallBack : DiffUtil.ItemCallback<FullInfoAnimeLG>() {

    // Compara si los ítems son el mismo (identidad)
    override fun areItemsTheSame(oldItem: FullInfoAnimeLG, newItem: FullInfoAnimeLG): Boolean {
        return oldItem.id == newItem.id
    }

    // Compara el contenido de los ítems (igualdad)
    override fun areContentsTheSame(oldItem: FullInfoAnimeLG, newItem: FullInfoAnimeLG): Boolean {
        return oldItem == newItem
    }
}
