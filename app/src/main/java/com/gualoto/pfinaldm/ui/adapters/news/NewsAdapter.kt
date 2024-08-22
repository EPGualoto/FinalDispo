package com.gualoto.pfinaldm.ui.adapters.news

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gualoto.pfinaldm.data.network.entities.news.Data
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.ItemNewBinding

class NewsAdapter : ListAdapter<Data, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        // Inflar el layout item_new.xml
        val binding = ItemNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }

    class NewsViewHolder(private val binding: ItemNewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: Data) {
            binding.newsTitle.text = news.title
            binding.newsAuthor.text = "By ${news.author_username}"
            binding.newsDate.text = news.date
            binding.newsComments.text = "Comments: ${news.comments}"

            val imageUrl = news.images.jpg.image_url

            // Mostrar una imagen predeterminada si la URL está vacía o es nula
            Glide.with(binding.newsImage.context)
                .load(imageUrl)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.sinfoto) // Imagen de marcador de posición
                        .error(R.drawable.sinfoto)) // Imagen de marcador de error
                .into(binding.newsImage)

            binding.root.setOnClickListener {
                val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.dialog_news_detail, null)

                // Obtener referencias a los elementos del diálogo
                val titleTextView = dialogView.findViewById<TextView>(R.id.titleTextView)
                val authorTextView = dialogView.findViewById<TextView>(R.id.authorTextView)
                val dateTextView = dialogView.findViewById<TextView>(R.id.dateTextView)
                val commentsTextView = dialogView.findViewById<TextView>(R.id.commentsTextView)
                val excerptTextView = dialogView.findViewById<TextView>(R.id.excerptTextView)
                val largeImageView = dialogView.findViewById<ImageView>(R.id.imageView)

                // Configurar los datos del diálogo
                titleTextView.text = news.title
                authorTextView.text = "by ${news.author_username}"
                dateTextView.text = news.date // Deberás formatear la fecha si es necesario
                commentsTextView.text = "${news.comments} Comments"
                excerptTextView.text = "${news.excerpt} [Written by MAL Rewrite]"

                // Cargar la imagen grande en el diálogo
                Glide.with(dialogView.context)
                    .load(imageUrl)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.sinfoto) // Imagen de marcador de posición
                            .error(R.drawable.sinfoto)) // Imagen de marcador de error
                    .into(largeImageView)

                val dialog = AlertDialog.Builder(itemView.context)
                    .setView(dialogView)
                    .setPositiveButton("OPEN URL") { _, _ ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
                        itemView.context.startActivity(intent)
                    }
                    .setNegativeButton("CLOSE", null)
                    .create()
                dialog.show()
            }
        }
    }

}

private class NewsDiffCallback : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data) = oldItem.mal_id == newItem.mal_id
    override fun areContentsTheSame(oldItem: Data, newItem: Data) = oldItem == newItem
}
