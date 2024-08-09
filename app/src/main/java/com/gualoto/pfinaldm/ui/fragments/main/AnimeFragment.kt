package com.gualoto.pfinaldm.ui.fragments.main

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.gualoto.pfinaldm.data.network.entities.animet.JikanApi
import com.gualoto.pfinaldm.data.network.repository.RetrofitClient
import com.gualoto.pfinaldm.databinding.FragmentAnimeBinding
import com.gualoto.pfinaldm.ui.adapters.AnimeAdapter

class AnimeFragment : Fragment() {

    private lateinit var binding: FragmentAnimeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animeAdapter = AnimeAdapter()
        binding.animeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.animeRecyclerView.adapter = animeAdapter

        binding.searchButton.setOnClickListener {
            val query = binding.searchQuery.text.toString()
            if (query.isNotEmpty()) {
                (binding.animeRecyclerView.adapter as AnimeAdapter).submitList(emptyList()) // Limpiar el RecyclerView
                fetchAnime(query)
            }
        }
    }

    private fun fetchAnime(query: String) {
        showLottieAnimation(true)
        RetrofitClient.instance.getAnime(query, 1).enqueue(object : Callback<JikanApi> {
            override fun onResponse(call: Call<JikanApi>, response: Response<JikanApi>) {
                showLottieAnimation(false)
                if (response.isSuccessful) { // Verifica si la respuesta fue exitosa
                    val animeList = response.body()?.data ?: emptyList()
                    if (animeList.isEmpty()) {
                        showNoResultsDialog()
                    } else {
                        (binding.animeRecyclerView.adapter as AnimeAdapter).submitList(animeList)
                    }
                } else {
                    showNoResultsDialog()
                }
            }

            override fun onFailure(call: Call<JikanApi>, t: Throwable) {
                showLottieAnimation(false)
                showNoResultsDialog()
            }
        })
    }

    private fun showLottieAnimation(show: Boolean) {
        if (show) {
            binding.lottieAnimationView.visibility = View.VISIBLE
            binding.lottieAnimationView.playAnimation()
        } else {
            binding.lottieAnimationView.visibility = View.GONE
            binding.lottieAnimationView.cancelAnimation()
        }
    }

    private fun showNoResultsDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("--- LO SIENTO ---")
            .setMessage("No se encontró el anime.")
            .setPositiveButton("OK", null)
            .create()
        dialog.show()
    }
}
