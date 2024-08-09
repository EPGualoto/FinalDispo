package com.gualoto.pfinaldm.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG
import com.gualoto.pfinaldm.databinding.NewsItemsBinding
import com.gualoto.pfinaldm.databinding.UsersItemsBinding

class NewsAdapterDiffUtil(
private val onDeleteItem: (Int) -> Unit,
private val onSelectItem: (FullInfoAnimeLG) -> Unit
) : ListAdapter<FullInfoAnimeLG, NewsAdapterDiffUtil.NewsViewHolder>(DiffUtilFullInfoAnimeCallBack) {

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NewsItemsBinding.bind(view)

        fun bind(item: FullInfoAnimeLG) {
            binding.apply {
                NewsName.text = item.name
                NewsComment.text = item.sypnosis
                News.text = item.sypnosis
                Views.text = item.type.toString()
                imgNews.load(item.big_image)
                btnBorrar.setOnClickListener {
                    onDeleteItem(adapterPosition)
                }
                imgNews.setOnClickListener {
                    onSelectItem(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.news_items, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}