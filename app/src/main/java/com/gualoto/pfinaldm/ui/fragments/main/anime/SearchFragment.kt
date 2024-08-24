package com.gualoto.pfinaldm.ui.fragments.main.anime

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentSearchBinding
import com.gualoto.pfinaldm.ui.adapters.anime.AnimePagerAdapter
import android.content.Context
import android.widget.AdapterView
import android.widget.ArrayAdapter

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var spinnerAdapter: ArrayAdapter<String>

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

        val animeTypes = listOf("all", "tv", "movie", "ova", "special", "ona", "music", "cm", "pv", "tv_special")
        val animeTypesUpperCase = animeTypes.map { it.uppercase() }

        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, animeTypesUpperCase)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = if (position == 0) null else animeTypes[position].lowercase()
                val adapter = AnimePagerAdapter(childFragmentManager, lifecycle)
                binding.viewPager.adapter = adapter
                (binding.viewPager.adapter as? AnimePagerAdapter)?.updateAnimeType(selectedType)

                // Actualizar el TextView con la selección actual
                binding.selectedAnimeTypeTextView.text = animeTypesUpperCase[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        updateProfileIcon()
    }

    private fun updateProfileIcon() {
        val profileImageResId = sharedPreferences.getInt("profileImage", R.drawable.ic_profile)
        binding.profileIcon.setImageResource(profileImageResId)
    }
}
