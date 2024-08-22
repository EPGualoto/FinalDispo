package com.gualoto.pfinaldm.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gualoto.pfinaldm.databinding.ActivityLoginaBinding


class LoginaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splash = installSplashScreen()

        binding= ActivityLoginaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splash.setKeepOnScreenCondition{ false }
        Log.d("LoginActivity", "onCreate: finished")
    }
}