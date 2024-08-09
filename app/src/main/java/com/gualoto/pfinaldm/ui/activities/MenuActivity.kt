package com.gualoto.pfinaldm.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.ActivityLoginaBinding
import com.gualoto.pfinaldm.ui.fragments.main.MenuFragment


class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // Agregar el fragmento solo si no hay un estado guardado
            supportFragmentManager.beginTransaction()
                .replace(R.id.menu, MenuFragment())
                .commit()
        }
    }
}