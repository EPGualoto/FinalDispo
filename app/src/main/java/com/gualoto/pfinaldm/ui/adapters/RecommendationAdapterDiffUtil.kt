package com.gualoto.pfinaldm.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG
import com.gualoto.pfinaldm.databinding.NewsItemsBinding
import com.gualoto.pfinaldm.databinding.UsersItemsBinding

class RecommendationAdapterDiffUtil:Fragment(){
    private lateinit var recommendationsAdapter: UsersAdapterDiffUtil

   /* override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    val view = inflater.inflate(R.layout.fragment_reco, container, false)
    //val recyclerView: RecyclerView = view.findViewById(R.id.Views)
    recyclerView.layoutManager = LinearLayoutManager(context)

    recommendationsAdapter = UsersAdapterDiffUtil(
        onDeleteItem = { position -> /* handle delete */ },
        onSelectItem = { item -> /* handle select */ }
    )

    recyclerView.adapter = recommendationsAdapter
    return view
}

fun submitRecommendationsList(recommendationsList: List<FullInfoAnimeLG>) {
    recommendationsAdapter.submitList(recommendationsList)
}*/
}