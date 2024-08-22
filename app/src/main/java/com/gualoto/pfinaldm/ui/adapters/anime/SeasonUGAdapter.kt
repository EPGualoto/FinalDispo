package com.gualoto.pfinaldm.ui.adapters.anime

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gualoto.pfinaldm.databinding.ItemAnimegBinding
import com.gualoto.pfinaldm.ui.core.SeasonUAnimeUI

class SeasonUGAdapter : ListAdapter<SeasonUAnimeUI, SeasonUGAdapter.AnimeViewHolder>(
    AnimeDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimegBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AnimeViewHolder(private val binding: ItemAnimegBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SeasonUAnimeUI) {
            binding.animeTitle.text = data.title
            Glide.with(binding.animeImage.context)
                .load(data.imageUrl)
                .into(binding.animeImage)
        }
    }

    class AnimeDiffCallback : DiffUtil.ItemCallback<SeasonUAnimeUI>() {
        override fun areItemsTheSame(oldItem: SeasonUAnimeUI, newItem: SeasonUAnimeUI) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SeasonUAnimeUI, newItem: SeasonUAnimeUI) = oldItem == newItem
    }
}