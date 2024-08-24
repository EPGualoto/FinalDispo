package com.gualoto.pfinaldm.ui.adapters.anime

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gualoto.pfinaldm.ui.fragments.main.anime.AnimeTypeFragment

class AnimePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private var animeType: String? = null

    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        val fragment = AnimeTypeFragment()
        val args = Bundle()
        if (animeType != null) {
            args.putString("anime_type", animeType)
        }
        fragment.arguments = args
        return fragment
    }

    fun updateAnimeType(type: String?) {
        animeType = type
        notifyDataSetChanged()
    }
}


