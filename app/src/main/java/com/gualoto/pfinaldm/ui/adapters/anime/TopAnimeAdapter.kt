package com.gualoto.pfinaldm.ui.adapters.anime

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gualoto.pfinaldm.databinding.ItemAnimegBinding
import com.gualoto.pfinaldm.ui.core.TopAnimeUI

class TopAnimeAdapter(private val onClickAction: (TopAnimeUI) -> Unit) : ListAdapter<TopAnimeUI, TopAnimeAdapter.TopAnimeViewHolder>(TopAnimeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAnimeViewHolder {
        val binding = ItemAnimegBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopAnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopAnimeViewHolder, position: Int) {
        holder.bind(getItem(position), onClickAction)
    }

    class TopAnimeViewHolder(private val binding: ItemAnimegBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: TopAnimeUI, onClickAction: (TopAnimeUI) -> Unit) {
            binding.animeTitle.text = anime.title
            Glide.with(binding.animeImage.context)
                .load(anime.imageUrl)
                .into(binding.animeImage)

            itemView.setOnClickListener {
                onClickAction(anime)
            }
        }

    }

    class TopAnimeDiffCallback : DiffUtil.ItemCallback<TopAnimeUI>() {
        override fun areItemsTheSame(oldItem: TopAnimeUI, newItem: TopAnimeUI) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: TopAnimeUI, newItem: TopAnimeUI) = oldItem == newItem
    }
}