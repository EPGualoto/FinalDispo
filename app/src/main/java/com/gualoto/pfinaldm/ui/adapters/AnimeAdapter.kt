package com.gualoto.pfinaldm.ui.adapters

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gualoto.pfinaldm.data.network.entities.jikan.Data
import com.bumptech.glide.Glide
import com.gualoto.pfinaldm.databinding.AnimeItemBinding

class AnimeAdapter : ListAdapter<Data, AnimeAdapter.AnimeViewHolder>(AnimeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = AnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = getItem(position)
        holder.bind(anime)
    }

    class AnimeViewHolder(private val binding: AnimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: Data) {
            binding.animeTitle.text = anime.title

            Glide.with(binding.animeImage.context)
                .load(anime.images.jpg.image_url)
                .into(binding.animeImage)

            binding.animeImage.setOnClickListener {
                if (anime.trailer.url != null) {
                    // Mostrar un dialog con la sinopsis y la URL
                    val dialog = AlertDialog.Builder(itemView.context)
                        .setTitle(anime.title)
                        .setMessage("Synopsis: ${anime.synopsis}\n\nURL Trailer: ${anime.trailer.url}")
                        .setPositiveButton("Abrir URL") { _, _ ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(anime.trailer.url))
                            itemView.context.startActivity(intent)
                        }
                        .setNegativeButton("Cerrar", null)
                        .create()
                    dialog.show()
                } else {
                    // Mostrar un log diciendo que el anime no tiene URL
                    Log.d("AnimeAdapter", "Anime No Tiene URL: ${anime.title}")
                    val dialog = AlertDialog.Builder(itemView.context)
                        .setTitle(anime.title)
                        .setMessage("Synopsis: ${anime.synopsis}\n\nURL Trailer: No disponible")
                        .setNegativeButton("Cerrar", null)
                        .create()
                    dialog.show()
                }
            }
        }
    }
}

private class AnimeDiffCallback : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data) = oldItem.mal_id == newItem.mal_id
    override fun areContentsTheSame(oldItem: Data, newItem: Data) = oldItem == newItem
}
