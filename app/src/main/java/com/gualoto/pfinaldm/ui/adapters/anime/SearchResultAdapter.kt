package com.gualoto.pfinaldm.ui.adapters.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gualoto.pfinaldm.data.network.entities.busqueda.anime.Data
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.Locale

import com.google.gson.Gson
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.AnimeItemBinding
import com.gualoto.pfinaldm.ui.fragments.main.anime.AnimeDetailFragment

class SearchResultAdapter :
    ListAdapter<Data, SearchResultAdapter.SearchViewHolder>(SearchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class SearchViewHolder(private val binding: AnimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            binding.animeTitle.text = item.title
            binding.animeType.text = item.type
            binding.animeEpisodes.text = "${item.episodes} ep"
            binding.animeSeason.text = item.season?.replaceFirstChar { it.uppercase() } ?: ""
            binding.animeYear.text = item.aired.prop.from.year?.toString()

            val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
            binding.animeMembers.text = numberFormat.format(item.members)
            binding.animeScore.text = item.score.toString()

            Glide.with(binding.animeImage.context)
                .load(item.images.jpg.image_url)
                .into(binding.animeImage)

            binding.animeImage.setOnClickListener {
                val context = binding.animeImage.context
                val fragment = AnimeDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString("anime_data", Gson().toJson(item)) // Convertir a JSON
                    }
                }
                (context as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.containerFragments, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }
    }
}


private class SearchDiffCallback : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data) = oldItem.mal_id == newItem.mal_id
    override fun areContentsTheSame(oldItem: Data, newItem: Data) = oldItem == newItem
}
