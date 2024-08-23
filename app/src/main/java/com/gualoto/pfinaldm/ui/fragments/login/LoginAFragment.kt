package com.gualoto.pfinaldm.ui.fragments.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentLoginaBinding
import com.gualoto.pfinaldm.ui.activities.MainActivity
import com.gualoto.pfinaldm.ui.core.ManageUIStates
import com.gualoto.pfinaldm.ui.core.UIStates
import com.gualoto.pfinaldm.ui.fragments.main.anime.AnimeGFragment
import com.gualoto.pfinaldm.ui.viewmodels.login.LoginFragmentVM
import java.util.concurrent.Executor

class LoginAFragment : Fragment() {
    private lateinit var binding: FragmentLoginaBinding
    private lateinit var managerUIStates: ManageUIStates
    private val loginFragmentVM: LoginFragmentVM by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    private val sharedPreferencesEditor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVariables()
        initListeners()
        initiObservers()
    }

    private fun initVariables() {
        auth = FirebaseAuth.getInstance()
        managerUIStates = ManageUIStates(requireActivity(), binding.lytLoading.mainLayout)
        sharedPreferences = requireContext().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    }


    private fun initiObservers() {
        loginFragmentVM.uiState.observe(viewLifecycleOwner) { state ->
            if (state is UIStates.Success) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            } else {
                managerUIStates.invoke(state)
            }
        }

        loginFragmentVM.idUser.observe(viewLifecycleOwner) { id ->
            startActivity(
                Intent(
                    requireActivity(),
                    AnimeGFragment::class.java
                )
            )
            requireActivity().finish()
        }
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment2)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etxtUser.text.toString()
            val password = binding.etxtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginFragmentVM.authWhitFireBase(email, password, auth, requireActivity())
            } else {
                Toast.makeText(requireContext(), "Ingresa tus credenciales", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
