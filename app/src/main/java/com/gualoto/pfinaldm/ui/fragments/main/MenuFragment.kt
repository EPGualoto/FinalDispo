package com.gualoto.pfinaldm.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        setupCardViewListeners()
        return binding.root
    }

    private fun setupCardViewListeners() {
        // Configura los listeners para los CardView
        binding.home.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment2_to_listFrament)
        }
        binding.recomendation.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment2_to_listFrament)
        }
        binding.image.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment2_to_charactersFragment)
        }
        binding.news.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment2_to_animeFragment)
        }
    }
}

