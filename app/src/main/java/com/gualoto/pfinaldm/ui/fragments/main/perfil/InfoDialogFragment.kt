package com.gualoto.pfinaldm.ui.fragments.main.perfil


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import com.gualoto.pfinaldm.R
import androidx.fragment.app.DialogFragment

class InfoDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_info_dialog, null)

        val imageView1 = view.findViewById<ImageView>(R.id.imageView)
        val imageView2 = view.findViewById<ImageView>(R.id.imageView2)
        val imageView3 = view.findViewById<ImageView>(R.id.imageView3)
        val imageView4 = view.findViewById<ImageView>(R.id.imageView4)
        val imageView5 = view.findViewById<ImageView>(R.id.imageView5)
        val imageView6 = view.findViewById<ImageView>(R.id.imageView6)

        val fadeIn = ObjectAnimator.ofFloat(imageView1, "alpha", 0f, 1f).apply {
            duration = 2000 // 1 segundo
        }
        val fadeIn2 = ObjectAnimator.ofFloat(imageView2, "alpha", 0f, 1f).apply {
            duration = 1000 // 1 segundo
        }
        val fadeIn3 = ObjectAnimator.ofFloat(imageView3, "alpha", 0f, 1f).apply {
            duration = 1000 // 1 segundo
        }
        val fadeIn4 = ObjectAnimator.ofFloat(imageView4, "alpha", 0f, 1f).apply {
            duration = 1000 // 1 segundo
        }
        val fadeIn5 = ObjectAnimator.ofFloat(imageView5, "alpha", 0f, 1f).apply {
            duration = 1000 // 1 segundo
        }
        val fadeIn6 = ObjectAnimator.ofFloat(imageView6, "alpha", 0f, 1f).apply {
            duration = 1000 // 1 segundo
        }

        val animationSet = AnimatorSet()
        animationSet.playSequentially(fadeIn, fadeIn2, fadeIn3, fadeIn4, fadeIn5, fadeIn6)
        animationSet.start()
        builder.setView(view)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
        return builder.create()
    }
}
