package com.gualoto.pfinaldm.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.gualoto.pfinaldm.data.network.entities.animet.JikanApi
import com.gualoto.pfinaldm.data.network.repository.RetrofitClient
import com.gualoto.pfinaldm.databinding.ActivityAnimeBinding
import com.gualoto.pfinaldm.ui.adapters.AnimeAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animeAdapter = AnimeAdapter()
        binding.animeRecyclerView.layoutManager = GridLayoutManager(this, 2)
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
                if (response.isSuccessful) {
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
        val dialog = AlertDialog.Builder(this)
            .setTitle("--- LO SIENTO ---")
            .setMessage("No se encontró el anime.")
            .setPositiveButton("OK", null)
            .create()
        dialog.show()
    }
}
