package com.gualoto.pfinaldm.ui.fragments.main.perfil

import android.app.AlertDialog
import android.app.Dialog
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
import com.gualoto.pfinaldm.ui.adapters.perfil.ViewPagerAdapter2
import android.content.Context
import android.content.SharedPreferences
import android.widget.GridView
import androidx.fragment.app.DialogFragment
import com.gualoto.pfinaldm.ui.adapters.perfil.ImageSelectionAdapter

class InfoDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_info_dialog, null)

        builder.setView(view)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }

        return builder.create()
    }
}