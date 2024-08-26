package com.gualoto.pfinaldm.ui.fragments.main.perfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.data.network.entities.busqueda.anime.Data
import com.gualoto.pfinaldm.databinding.FragmentFavoritosBinding
import com.gualoto.pfinaldm.ui.adapters.anime.TopAnimeAdapter
import java.util.ArrayList

class FavoritosFragment : Fragment() {

    private lateinit var binding: FragmentFavoritosBinding

    private lateinit var adapter: TopAnimeAdapter

    private var lista = ArrayList<Data>()
    private var fav = ArrayList<Data>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritosBinding.bind(inflater.inflate(R.layout.fragment_favoritos, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecycyler(){




    }


}
