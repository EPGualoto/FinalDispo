package com.gualoto.pfinaldm.ui.fragments.main.anime

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentAnimeSeasonGBinding
import com.gualoto.pfinaldm.ui.core.SeasonAnimeUI
import java.text.NumberFormat
import java.util.Locale

class AnimeSeasonGFragment : Fragment() {
    private lateinit var binding: FragmentAnimeSeasonGBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeSeasonGBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val jsonString: String? = arguments?.getString("anime_data")
        val data: SeasonAnimeUI? = jsonString?.let {
            Gson().fromJson(it, SeasonAnimeUI::class.java)
        }

        data?.let { anime ->
            binding.animeTitle.text = anime.title
            binding.animeType.text = "${anime.type}, "
            binding.animeYear.text = anime.aired.prop.from.year?.toString()
            binding.animeEpisodes.text = "${anime.episodes} ep"
            binding.animeMin.text = anime.duration
            binding.animeRank.text = "# ${anime.rank}" ?: "N/A"
            binding.animeFavorites.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(anime.favorites)
            binding.animePopularity.text = "# ${anime.popularity}"
            binding.animeScore.text = anime.score.toString()
            binding.animeMembers.text = NumberFormat.getNumberInstance(Locale.getDefault()).format(anime.members)
            binding.animeStatus.text = anime.status

            val allGenres = anime.genres.map { genre -> genre.name } +
                    anime.themes.map { theme -> theme.name }
            binding.animeGenres.text = allGenres.joinToString(separator = "  • ")
            binding.animeSynopsis.text = anime.synopsis

            Glide.with(binding.animeImage.context)
                .load(anime.imageUrl)
                .into(binding.animeImage)

            // Verifica que el tráiler no sea null y tenga una URL
            if (anime.trailer != null && !anime.trailer.url.isNullOrEmpty()) {
                // Mostrar el cuadro de tráiler si hay un tráiler disponible
                binding.animeTrailerThumbnail.visibility = View.VISIBLE
                binding.animeTrailerPlayButton.visibility = View.VISIBLE

                Glide.with(binding.animeTrailerThumbnail.context)
                    .load(anime.trailer.images.large_image_url)
                    .placeholder(R.drawable.background)
                    .into(binding.animeTrailerThumbnail)

                binding.animeTrailerPlayButton.setOnClickListener {
                    playTrailer(anime.trailer.url)
                }
            } else {
                // Ocultar el cuadro de tráiler si no hay un tráiler disponible
                binding.animeTrailerThumbnail.visibility = View.GONE
                binding.animeTrailerPlayButton.visibility = View.GONE

                // O puedes mostrar un mensaje si el usuario hace clic en el botón de reproducción
                binding.animeTrailerPlayButton.setOnClickListener {
                    Toast.makeText(context, "No trailer available for this anime", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun playTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }
}