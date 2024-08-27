package com.gualoto.pfinaldm.ui.fragments.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentRegisterBinding
import com.gualoto.pfinaldm.ui.core.ManageUIStates
import com.gualoto.pfinaldm.ui.viewmodels.login.RegisterFragmentVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val registerFragmentVM: RegisterFragmentVM by viewModels()
    private lateinit var managerUIStates: ManageUIStates
    private lateinit var auth: FirebaseAuth
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.bind(
            inflater.inflate(R.layout.fragment_register, container, false)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVariables()
        initListeners()
        initObservers()
    }

    private fun initVariables() {
        managerUIStates = ManageUIStates(requireActivity(), binding.lytLoading.mainLayout)
        auth = FirebaseAuth.getInstance()
    }

    private fun initObservers() {
        registerFragmentVM.uiState.observe(viewLifecycleOwner) { states ->
            managerUIStates.invoke(states)
        }
    }

    private fun initListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragment2ToLoginFragment())
        }

        binding.btnSignUp.setOnClickListener {
            val email = binding.edtxtSignUpEmail.text.toString().trim()
            val password = binding.edtxtSignUpPasswordConfirm.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireActivity(), "Email y contraseña no pueden estar vacíos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        saveUserToFirestore()
                    } else {
                        Toast.makeText(requireActivity(), task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun saveUserToFirestore() {
        val user = auth.currentUser ?: return
        val userData = hashMapOf(
            "nombre" to binding.edtxtSignUpName.text.toString().trim(),
            "apellido" to binding.edtxtSignUpLastName.text.toString().trim(),
            "email" to binding.edtxtSignUpEmail.text.toString().trim(),
            "password" to binding.edtxtSignUpPassword.text.toString().trim(),
            "confirmp" to binding.edtxtSignUpPasswordConfirm.text.toString().trim()
        )

        firestore.collection("users").document(user.uid).set(userData)
            .addOnSuccessListener {
                Toast.makeText(requireActivity(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragment2ToLoginFragment())
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireActivity(), e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}
