package com.gualoto.pfinaldm.ui.fragments.main.anime

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.gualoto.pfinaldm.data.network.entities.busqueda.anime.SearchAnimeApi
import com.gualoto.pfinaldm.data.network.repository.RetrofitClient
import com.gualoto.pfinaldm.databinding.FragmentAnimeTypeBinding
import com.gualoto.pfinaldm.ui.adapters.anime.SearchResultAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnimeTypeFragment : Fragment() {
    private lateinit var binding: FragmentAnimeTypeBinding
    private lateinit var adapter: SearchResultAdapter
    private var animeType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animeType = arguments?.getString("anime_type")

        adapter = SearchResultAdapter()
        binding.searchRecyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.searchRecyclerView.adapter = adapter

        // Initialize data fetch
        fetchResults()
    }

    private fun fetchResults() {
        val call = if (animeType == null) {
            RetrofitClient.instance.searchAllAnimeTypes(1)
        } else {
            RetrofitClient.instance.searchAnimeType(animeType!!, 1)
        }

        call.enqueue(object : Callback<SearchAnimeApi> {
            override fun onResponse(call: Call<SearchAnimeApi>, response: Response<SearchAnimeApi>) {
                if (response.isSuccessful) {
                    val resultList = response.body()?.data ?: emptyList()
                    adapter.submitList(resultList)
                } else {
                    showNoResultsDialog()
                }
            }

            override fun onFailure(call: Call<SearchAnimeApi>, t: Throwable) {
                showNoResultsDialog()
            }
        })
    }

    private fun showNoResultsDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("--- LO SIENTO ---")
            .setMessage("No se encontraron resultados.")
            .setPositiveButton("OK", null)
            .create()
        dialog.show()
    }
}
