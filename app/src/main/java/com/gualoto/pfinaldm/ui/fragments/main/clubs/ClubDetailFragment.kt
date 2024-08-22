package com.gualoto.pfinaldm.ui.fragments.main.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gualoto.pfinaldm.data.network.entities.clubs.fullClub.Data
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentClubDetailBinding
import com.gualoto.pfinaldm.ui.adapters.clubs.ViewPagerAdapter
import java.text.SimpleDateFormat
import java.util.Locale

class ClubDetailFragment : Fragment() {
    private lateinit var binding: FragmentClubDetailBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClubDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun formatNumber(number: Int): String {
        return when {
            number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000.0)
            number >= 1_000 -> String.format("%.1fk", number / 1_000.0)
            else -> number.toString()
        }
    }

    fun getColorFromClubName(clubName: String): Int {
        val colors = listOf(
            R.color.pink,
            R.color.purple,
            R.color.light_pink,
            R.color.light_green,
            R.color.light_yellow,
            R.color.blue_grey,
            R.color.deep_orange
        )
        val index = Math.abs(clubName.hashCode()) % colors.size
        return colors[index]
    }

    fun formatDate(isoDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return try {
            val date = inputFormat.parse(isoDate)
            outputFormat.format(date)
        } catch (e: Exception) {
            isoDate // Devuelve la fecha original si falla el parseo
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val jsonString: String? = arguments?.getString("club_data")
        val data: Data? = jsonString?.let {
            Gson().fromJson(it, Data::class.java)
        }

        // Configura el adaptador para ViewPager2
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Miembros"
                else -> ""
            }
        }.attach()

        data?.let { club ->
            // Configura la vista con los detalles del club
            binding.clubName.text = club.name
            binding.clubNameS.text = club.name
            binding.clubCreated.text = "Creado: ${formatDate(club.created)}"
            binding.clubGroup.text = formatNumber(club.members)

            // Obtén el color del club
            val colorResId = getColorFromClubName(club.name)
            val color = ContextCompat.getColor(requireContext(), colorResId)

            // Cambia el color de la barra superior (topBar)
            binding.topBar.setBackgroundColor(color)

            // Cambia el color de fondo del TabLayout para que coincida
            binding.tabLayout.setBackgroundColor(color)

            Glide.with(binding.clubImage.context)
                .load(club.images.jpg.image_url)
                .into(binding.clubImage)

            // Pasa el club ID al fragmento de miembros
            val membersFragment = ClubMembersFragment().apply {
                arguments = Bundle().apply {
                    putInt("club_id", club.mal_id)
                }
            }

            viewPagerAdapter.setFragmentData(membersFragment)
        }
    }
}

