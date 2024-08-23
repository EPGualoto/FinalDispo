package com.gualoto.pfinaldm.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.ActivityMainBinding
import com.gualoto.pfinaldm.databinding.FragmentAnimeGBinding
import com.gualoto.pfinaldm.ui.fragments.home.HomeFragment
import com.gualoto.pfinaldm.ui.fragments.main.anime.AnimeGFragment
import com.gualoto.pfinaldm.ui.fragments.main.anime.SearchFragment
import com.gualoto.pfinaldm.ui.fragments.main.clubs.ClubSearchFragment
import com.gualoto.pfinaldm.ui.fragments.main.news.NewsFragment
import com.gualoto.pfinaldm.ui.fragments.main.perfil.PerfilFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setImmersiveMode()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()

        // Mostrar el fragmento de bienvenida primero
        showWelcomeFragment()
    }

    private fun showWelcomeFragment() {
        // Cargar el fragmento de bienvenida (HomeFragment)
        supportFragmentManager.beginTransaction()
            .replace(binding.containerFragments.id, HomeFragment())
            .commit()

        Handler(Looper.getMainLooper()).postDelayed({
            supportFragmentManager.beginTransaction()
                .replace(binding.containerFragments.id, AnimeGFragment())
                .commit()
        }, 4000) // 4000 milisegundos = 4 segundos
    }

    private fun initListeners() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.item1 -> {
                    val x = supportFragmentManager.beginTransaction()
                    x.replace(binding.containerFragments.id, AnimeGFragment())
                    x.commit()
                    true
                }
                R.id.item2 -> {
                    val x = supportFragmentManager.beginTransaction()
                    x.replace(binding.containerFragments.id, ClubSearchFragment())
                    x.commit()
                    true
                }
                R.id.item3 -> {
                    val x = supportFragmentManager.beginTransaction()
                    x.replace(binding.containerFragments.id, SearchFragment())
                    x.commit()
                    true
                }
                R.id.item4 -> {
                    val x = supportFragmentManager.beginTransaction()
                    x.replace(binding.containerFragments.id, NewsFragment())
                    x.commit()
                    true
                }
                R.id.item5 -> {
                    val x = supportFragmentManager.beginTransaction()
                    x.replace(binding.containerFragments.id, PerfilFragment())
                    x.commit()
                    true
                }
                else -> false
            }
        }
    }

    private fun setImmersiveMode() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )

        // Configura un listener para restaurar el modo inmersivo si cambia la visibilidad del sistema
        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_FULLSCREEN
                        )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Configura el modo inmersivo cada vez que la actividad se reanude
        setImmersiveMode()
    }
}
