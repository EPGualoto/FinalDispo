package com.gualoto.pfinaldm.ui.adapters.perfil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.gualoto.pfinaldm.R

class ImageSelectionAdapter(
    private val context: Context,
    private val images: Array<Int>,
    private var selectedPosition: Int,
    private val onImageSelected: (Int) -> Unit) : ArrayAdapter<Int>(context, 0, images) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_imagen_selection, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        imageView.setImageResource(images[position])

         if (position == selectedPosition) {
            imageView.setBackgroundResource(R.drawable.image_selec_background)
        } else {
            imageView.setBackgroundResource(0)
        }

        imageView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
            onImageSelected(images[position])
        }
        return view
    }
}
