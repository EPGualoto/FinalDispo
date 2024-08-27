package com.gualoto.pfinaldm.ui.adapters.clubs

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gualoto.pfinaldm.ui.fragments.main.clubs.ClubMembersFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private var membersFragment: ClubMembersFragment? = null

    fun setFragmentData(fragment: ClubMembersFragment) {
        membersFragment = fragment
    }

    override fun getItemCount(): Int = 1 // Número de pestañas

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> membersFragment ?: ClubMembersFragment()
            else -> throw IllegalStateException("Unexpected position $position")

        }
    }
}

