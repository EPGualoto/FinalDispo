package com.gualoto.pfinaldm.ui.adapters.anime

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gualoto.pfinaldm.databinding.ItemAnimegBinding
import com.gualoto.pfinaldm.ui.core.SeasonAnimeUI

class SeasonGAdapter : ListAdapter<SeasonAnimeUI, SeasonGAdapter.AnimeViewHolder>(AnimeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimegBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AnimeViewHolder(private val binding: ItemAnimegBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SeasonAnimeUI) {
            binding.animeTitle.text = data.title
            Glide.with(binding.animeImage.context)
                .load(data.imageUrl)
                .into(binding.animeImage)

        }
    }

    class AnimeDiffCallback : DiffUtil.ItemCallback<SeasonAnimeUI>() {
        override fun areItemsTheSame(oldItem: SeasonAnimeUI, newItem: SeasonAnimeUI) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SeasonAnimeUI, newItem: SeasonAnimeUI) = oldItem == newItem
    }
}