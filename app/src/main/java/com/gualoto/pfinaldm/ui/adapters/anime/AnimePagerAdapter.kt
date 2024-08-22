package com.gualoto.pfinaldm.ui.adapters.anime

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gualoto.pfinaldm.ui.fragments.main.anime.AnimeTypeFragment

class AnimePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val animeTypes = listOf("tv", "movie", "ova", "special", "ona", "music", "cm", "pv", "tv_special")

    override fun getItemCount(): Int = animeTypes.size

    override fun createFragment(position: Int): Fragment {
        val fragment = AnimeTypeFragment()
        val args = Bundle()
        args.putString("anime_type", animeTypes[position])
        fragment.arguments = args
        return fragment
    }
}

