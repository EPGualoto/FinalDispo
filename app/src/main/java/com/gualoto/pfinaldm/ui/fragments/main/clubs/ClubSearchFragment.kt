package com.gualoto.pfinaldm.ui.fragments.main.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.ui.adapters.clubs.ClubAdapter
import com.gualoto.pfinaldm.ui.viewmodels.clubs.ClubSearchViewModel

class ClubSearchFragment : Fragment() {
    private lateinit var viewModel: ClubSearchViewModel
    private lateinit var clubAdapter: ClubAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_club_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ClubSearchViewModel::class.java)
        clubAdapter = ClubAdapter(listOf())

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewClubs)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = clubAdapter

        val searchView: SearchView = view.findViewById(R.id.searchViewClubs)

        // Cargar todos los clubes al iniciar
        viewModel.getAllClubs()

        // Escuchar cambios en el LiveData y actualizar el RecyclerView
        viewModel.clubs.observe(viewLifecycleOwner) { clubs ->
            clubAdapter.updateClubs(clubs)
        }

        // Configurar el listener para la barra de búsqueda
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchClubs(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
