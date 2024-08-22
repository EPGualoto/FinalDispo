package com.gualoto.pfinaldm.ui.fragments.main.perfil

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentPerfilBinding
import com.gualoto.pfinaldm.ui.adapters.ViewPagerAdapter2
import android.content.Context
import android.content.SharedPreferences

class PerfilFragment : Fragment() {
    private lateinit var binding: FragmentPerfilBinding
    private lateinit var auth: FirebaseAuth
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private lateinit var sharedPreferences: SharedPreferences

    private var selectedProfileImage: Int = R.drawable.ic_profile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)

        selectedProfileImage = sharedPreferences.getInt("profileImage", R.drawable.ic_profile)
        binding.imagePerfil.setImageResource(selectedProfileImage)

        loadUserProfile()
        setupViewPagerAndTabs()
        setupEditProfileImageButton()
    }

    private fun loadUserProfile() {
        val user = auth.currentUser
        user?.let {
            firestore.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val name = document.getString("nombre") ?: "Nombre no disponible"
                        binding.txtNameJugador.text = name
                    } else {
                        binding.txtNameJugador.text = "Nombre no disponible"
                    }
                }
                .addOnFailureListener { e ->
                    binding.txtNameJugador.text = "Error al cargar nombre"
                }
        }
    }

    private fun setupViewPagerAndTabs() {
        val viewPagerAdapter = ViewPagerAdapter2(requireActivity())
        binding.lytEstadisticas.adapter = viewPagerAdapter

        // Configura el TabLayout con el ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.lytEstadisticas) { tab, position ->
            tab.text = when (position) {
                0 -> "Favoritos"
                1 -> "Recientes"
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }.attach()
    }

    private fun setupEditProfileImageButton() {
        binding.btnEditProfileImage.setOnClickListener {
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Cambiar imagen de perfil")
            .setMessage("¿Deseas cambiar tu imagen de perfil?")
            .setPositiveButton("Aceptar") { _, _ ->
                showImageSelectionDialog()
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }

    private fun showImageSelectionDialog() {
        val images = arrayOf(R.drawable.perfil1, R.drawable.perfil2, R.drawable.perfil3)

        AlertDialog.Builder(requireContext())
            .setTitle("Selecciona una imagen de perfil")
            .setItems(arrayOf("Imagen perfil 1", "Imagen perfil 2", "Imagen perfil 3")) { _, which ->
                selectedProfileImage = images[which]
                binding.imagePerfil.setImageResource(selectedProfileImage)

                sharedPreferences.edit()
                    .putInt("profileImage", selectedProfileImage)
                    .apply()
            }
            .create()
            .show()
    }

    fun setCurrentTab(tabIndex: Int) {
        binding.lytEstadisticas.currentItem = tabIndex
    }
}