package com.gualoto.pfinaldm.ui.fragments.main.perfil

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentPerfilBinding
import com.gualoto.pfinaldm.ui.adapters.perfil.ImageSelectionAdapter
import com.gualoto.pfinaldm.ui.fragments.login.LoginAFragment

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

        // Carga la imagen de perfil seleccionada previamente
        selectedProfileImage = sharedPreferences.getInt("profileImage", R.drawable.ic_profile)
        binding.imagePerfil.setImageResource(selectedProfileImage)

        // Carga el perfil de usuario desde Firestore
        loadUserProfile()

        // Configura el botón para editar la imagen de perfil
        setupEditProfileImageButton()

        // Configura el botón para mostrar el diálogo de información
        binding.btnSettings.setOnClickListener {
            val dialogFragment = InfoDialogFragment()
            dialogFragment.show(parentFragmentManager, "infoDialog")
        }

        //Cerrar sesion
        binding.btnSignOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), LoginAFragment::class.java)
            startActivity(intent)
        }
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
        val images = arrayOf(
            R.drawable.perfil1, R.drawable.perfil2, R.drawable.perfil3,
            R.drawable.perfil4, R.drawable.perfil5, R.drawable.perfil6,
            R.drawable.perfil7, R.drawable.perfil8, R.drawable.perfil9,
            R.drawable.perfil10
        )
        var selectedPosition = images.indexOf(selectedProfileImage)

        val adapter = ImageSelectionAdapter(requireContext(), images, selectedPosition) { selectedImage ->
            selectedProfileImage = selectedImage
        }

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_image_selection, null)
        val gridView = dialogView.findViewById<GridView>(R.id.gridView)
        gridView.adapter = adapter

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Aceptar") { _, _ ->
                binding.imagePerfil.setImageResource(selectedProfileImage)
                sharedPreferences.edit().putInt("profileImage", selectedProfileImage).apply()
            }
            .setNegativeButton("Cancelar", null)
            .create()
            .show()
    }
}
