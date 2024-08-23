package com.gualoto.pfinaldm.ui.fragments.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private lateinit var sharedPreferences: SharedPreferences

    private var selectedProfileImage: Int = R.drawable.ic_profile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)

        selectedProfileImage = sharedPreferences.getInt("profileImage", R.drawable.ic_profile)
        binding.imagePerfil.setImageResource(selectedProfileImage)

        loadUserProfile()
    }

    private fun loadUserProfile() {
        val user = auth.currentUser
        user?.let {
            firestore.collection("users").document(user.uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val name = document.getString("nombre") ?: "Nombre no disponible"
                        binding.txtBienvenida.text = getString(R.string.welcome_message, name)
                    } else {
                        binding.txtBienvenida.text = getString(R.string.welcome_message, "Nombre no disponible")
                    }
                }
                .addOnFailureListener { e ->
                    binding.txtBienvenida.text = getString(R.string.welcome_message, "Error al cargar nombre")
                }
        }
    }
}
