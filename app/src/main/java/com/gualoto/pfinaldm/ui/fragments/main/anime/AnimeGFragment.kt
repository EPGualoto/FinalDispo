package com.gualoto.pfinaldm.ui.fragments.main.anime

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentAnimeGBinding
import com.gualoto.pfinaldm.ui.adapters.anime.SeasonGAdapter
import com.gualoto.pfinaldm.ui.adapters.anime.SeasonUGAdapter
import com.gualoto.pfinaldm.ui.adapters.anime.TopAnimeAdapter
import com.gualoto.pfinaldm.ui.viewmodels.anime.AnimeViewModel

class AnimeGFragment : Fragment() {
    private lateinit var binding: FragmentAnimeGBinding
    private val viewModel: AnimeViewModel by viewModels()
    private val topAnimeAdapter = TopAnimeAdapter()
    private val seasonGAdapter = SeasonGAdapter()
    private val seasonUGAdapter = SeasonUGAdapter()
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
}