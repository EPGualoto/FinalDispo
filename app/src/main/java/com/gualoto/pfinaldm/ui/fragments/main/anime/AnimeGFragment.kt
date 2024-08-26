package com.gualoto.pfinaldm.ui.fragments.main.anime

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.gson.Gson
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentAnimeGBinding
import com.gualoto.pfinaldm.ui.adapters.anime.SeasonGAdapter
import com.gualoto.pfinaldm.ui.adapters.anime.SeasonUGAdapter
import com.gualoto.pfinaldm.ui.adapters.anime.TopAnimeAdapter
import com.gualoto.pfinaldm.ui.core.SeasonAnimeUI
import com.gualoto.pfinaldm.ui.core.SeasonUAnimeUI
import com.gualoto.pfinaldm.ui.core.TopAnimeUI
import com.gualoto.pfinaldm.ui.viewmodels.anime.AnimeViewModel

class AnimeGFragment : Fragment() {
    private lateinit var binding: FragmentAnimeGBinding
    private val viewModel: AnimeViewModel by viewModels()
    private val topAnimeAdapter = TopAnimeAdapter{itemTop(it)}
    private val seasonGAdapter = SeasonGAdapter{itemSeasonG(it)}
    private val seasonUGAdapter = SeasonUGAdapter{itemSeasonUG(it)}
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeGBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        binding.recyclerViewSeasonNow.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = seasonGAdapter
            LinearSnapHelper().attachToRecyclerView(this)
        }

        viewModel.seasonNowAnimes.observe(viewLifecycleOwner) { animeList ->
            seasonGAdapter.submitList(animeList)
        }

        viewModel.getSeasonAnimes()

        // Configurar el RecyclerView para season upcoming
        binding.recyclerViewSeasonUAnime.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = seasonUGAdapter
            LinearSnapHelper().attachToRecyclerView(this)
        }

        viewModel.seasonUpcomingAnimes.observe(viewLifecycleOwner) { animeList ->
            seasonUGAdapter.submitList(animeList)
        }

        viewModel.getSeasonUpcomingAnimes()

        // Configurar el RecyclerView para los top animes
        binding.recyclerViewTopAnime.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topAnimeAdapter
            LinearSnapHelper().attachToRecyclerView(this)
        }

        viewModel.topAnimes.observe(viewLifecycleOwner) { animeList ->
            topAnimeAdapter.submitList(animeList)
        }

        viewModel.getTopAnimes()

        updateProfileIcon() // Actualiza el icono de perfil al cargar el fragmento
    }


    private fun updateProfileIcon() {
        val profileImageResId = sharedPreferences.getInt("profileImage", R.drawable.ic_profile)
        binding.profileIcon.setImageResource(profileImageResId)
    }

    private fun itemSeasonG(anime: SeasonAnimeUI){
        val context = binding.recyclerViewSeasonNow.context
        val fragment = AnimeSeasonGFragment().apply {
            arguments = Bundle().apply {
                putString("anime_data", Gson().toJson(anime)) // Convertir a JSON
            }
        }
        (context as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.containerFragments, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun itemSeasonUG(anime: SeasonUAnimeUI){
        val context = binding.recyclerViewSeasonUAnime.context
        val fragment = AnimeSeasonUGFragment().apply {
            arguments = Bundle().apply {
                putString("anime_data", Gson().toJson(anime)) // Convertir a JSON
            }
        }
        (context as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.containerFragments, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun itemTop(anime: TopAnimeUI){
        val context = binding.recyclerViewTopAnime.context
        val fragment = AnimeTopFragment().apply {
            arguments = Bundle().apply {
                putString("anime_data", Gson().toJson(anime)) // Convertir a JSON
            }
        }
        (context as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.containerFragments, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}