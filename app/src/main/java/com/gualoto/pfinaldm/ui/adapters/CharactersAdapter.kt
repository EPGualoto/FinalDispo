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
import com.gualoto.pfinaldm.data.network.entities.characters.Data
import com.bumptech.glide.Glide
import com.gualoto.pfinaldm.databinding.ItemCharacterBinding

class CharactersAdapter : ListAdapter<Data, CharactersAdapter.CharacterViewHolder>(
    CharacterDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Data) {
            binding.characterName.text = character.name

            Glide.with(binding.characterImage.context)
                .load(character.images.jpg.image_url)
                .into(binding.characterImage)

            binding.characterImage.setOnClickListener {
                if (character.url != null) {
                    // Mostrar un dialog con la información del personaje y la URL
                    val dialog = AlertDialog.Builder(itemView.context)
                        .setTitle(character.name)
                        .setMessage("About: ${character.about}\n\nURL: ${character.url}")
                        .setPositiveButton("Abrir URL") { _, _ ->
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(character.url))
                            itemView.context.startActivity(intent)
                        }
                        .setNegativeButton("Cerrar", null)
                        .create()
                    dialog.show()
                } else {
                    // Mostrar un log diciendo que el personaje no tiene URL
                    Log.d("CharactersAdapter", "Personaje No Tiene URL: ${character.name}")
                    val dialog = AlertDialog.Builder(itemView.context)
                        .setTitle(character.name)
                        .setMessage("About: ${character.about}\n\nURL: No disponible")
                        .setNegativeButton("Cerrar", null)
                        .create()
                    dialog.show()
                }
            }
        }
    }
}

private class CharacterDiffCallback : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data) = oldItem.mal_id == newItem.mal_id
    override fun areContentsTheSame(oldItem: Data, newItem: Data) = oldItem == newItem
}
