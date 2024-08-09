package com.gualoto.pfinaldm.ui.fragments.main

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.gualoto.pfinaldm.data.network.entities.characters.CharactersApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.gualoto.pfinaldm.data.network.repository.CharactersClient
import com.gualoto.pfinaldm.databinding.FragmentCharactersBinding
import com.gualoto.pfinaldm.ui.adapters.CharactersAdapter

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val charactersAdapter = CharactersAdapter()
        binding.charactersRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
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
            .setMessage("No se encontró el personaje.")
            .setPositiveButton("OK", null)
            .create()
        dialog.show()
    }
}
