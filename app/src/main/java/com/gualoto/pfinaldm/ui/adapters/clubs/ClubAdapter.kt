package com.gualoto.pfinaldm.ui.adapters.clubs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.gualoto.pfinaldm.data.network.entities.clubs.fullClub.Data
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.ui.fragments.main.clubs.ClubDetailFragment

class ClubAdapter(private var clubs: List<Data>) : RecyclerView.Adapter<ClubAdapter.ClubViewHolder>() {

    class ClubViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val clubName: TextView = view.findViewById(R.id.clubName)
        val clubDescription: TextView = view.findViewById(R.id.clubDescription)
        val clubMembers: TextView = view.findViewById(R.id.clubMembers)
        val clubImage: ImageView = view.findViewById(R.id.clubImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_club, parent, false)
        return ClubViewHolder(view)
    }

    fun formatNumber(number: Int): String {
        return when {
            number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000.0)
            number >= 1_000 -> String.format("%.1fk", number / 1_000.0)
            else -> number.toString()
        }
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val club = clubs[position]
        holder.clubName.text = club.name
        holder.clubDescription.text = club.access?.replaceFirstChar { it.uppercase() }
        holder.clubMembers.text = formatNumber(club.members)

        Glide.with(holder.itemView.context)
            .load(club.images.jpg.image_url)
            .into(holder.clubImage)

        holder.clubImage.setOnClickListener {
            val context = holder.itemView.context
            val fragment = ClubDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("club_data", Gson().toJson(club))
                }
            }
            (context as? AppCompatActivity)?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.containerFragments, fragment)
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun getItemCount() = clubs.size

    fun updateClubs(newClubs: List<Data>) {
        clubs = newClubs
        notifyDataSetChanged()
    }
}
