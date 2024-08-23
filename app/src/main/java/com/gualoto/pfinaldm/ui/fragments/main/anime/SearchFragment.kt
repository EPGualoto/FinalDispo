package com.gualoto.pfinaldm.ui.fragments.main.anime

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentSearchBinding
import com.gualoto.pfinaldm.ui.adapters.anime.AnimePagerAdapter

import android.content.Context

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)

        val adapter = AnimePagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "TV"
                1 -> "Movie"
                2 -> "OVA"
                3 -> "Special"
                4 -> "ONA"
                5 -> "Music"
                6 -> "CM"
                7 -> "PV"
                8 -> "TV Special"
                else -> "Unknown"
            }
        }.attach()
        updateProfileIcon() // Actualiza el icono de perfil al cargar el fragmento
    }

    private fun updateProfileIcon() {
        val profileImageResId = sharedPreferences.getInt("profileImage", R.drawable.ic_profile)
        binding.profileIcon.setImageResource(profileImageResId)
    }

}
