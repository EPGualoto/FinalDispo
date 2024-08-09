package com.gualoto.pfinaldm.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog

import androidx.navigation.fragment.NavHostFragment
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.ui.fragments.login.LoginAFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.containerFragments) as? NavHostFragment
        val fragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull()

        if (fragment is LoginAFragment) {
            super.onBackPressed()
        } else {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión")
            .setMessage("¿Quieres cerrar sesión?")
            .setPositiveButton("Sí") { dialog, _ ->
                dialog.dismiss()
                logout()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        // Volver al fragmento de login
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerFragments, LoginAFragment())
            .commit()
    }
}