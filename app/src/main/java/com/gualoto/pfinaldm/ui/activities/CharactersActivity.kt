package com.gualoto.pfinaldm.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.gualoto.pfinaldm.data.network.entities.characters.CharactersApi
import com.gualoto.pfinaldm.data.network.repository.CharactersClient
import com.gualoto.pfinaldm.databinding.ActivityCharactersBinding
import com.gualoto.pfinaldm.ui.adapters.CharactersAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val charactersAdapter = CharactersAdapter()
        binding.charactersRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.charactersRecyclerView.adapter = charactersAdapter

        binding.searchButton.setOnClickListener {
            val query = binding.searchQuery.text.toString()
            if (query.isNotEmpty()) {
                (binding.charactersRecyclerView.adapter as CharactersAdapter).submitList(emptyList()) // Limpiar el RecyclerView
                fetchCharacters(query)
            }
        }
    }

    private fun fetchCharacters(query: String) {
        showLottieAnimation(true)
        CharactersClient.instance.getCharacters(query, 1).enqueue(object : Callback<CharactersApi> {
            override fun onResponse(call: Call<CharactersApi>, response: Response<CharactersApi>) {
                showLottieAnimation(false)
                if (response.isSuccessful) {
                    val charactersList = response.body()?.data ?: emptyList()
                    if (charactersList.isEmpty()) {
                        showNoResultsDialog()
                    } else {
                        (binding.charactersRecyclerView.adapter as CharactersAdapter).submitList(charactersList)
                    }
                } else {
                    showNoResultsDialog()
                }
            }

            override fun onFailure(call: Call<CharactersApi>, t: Throwable) {
                showLottieAnimation(false)
                showNoResultsDialog()
                // Handle failure
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
            .setMessage("No se encontró el personaje.")
            .setPositiveButton("OK", null)
            .create()
        dialog.show()
    }
}
