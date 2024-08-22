package com.gualoto.pfinaldm.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gualoto.pfinaldm.ui.fragments.main.perfil.FavoritosFragment
import com.gualoto.pfinaldm.ui.fragments.main.perfil.RecientesFragment

class ViewPagerAdapter2(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 2 // Número de pestañas

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoritosFragment() // Fragmento para la pestaña "Favoritos"
            1 -> RecientesFragment() // Fragmento para la pestaña "Recientes"
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
