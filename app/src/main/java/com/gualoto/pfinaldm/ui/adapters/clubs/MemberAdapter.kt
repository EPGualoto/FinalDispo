package com.gualoto.pfinaldm.ui.adapters.clubs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gualoto.pfinaldm.data.network.entities.clubs.members.Data
import com.bumptech.glide.Glide
import com.gualoto.pfinaldm.R

class MemberAdapter(private var members: List<Data>) : RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    class MemberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val memberUsername: TextView = view.findViewById(R.id.memberUsername)
        val memberImage: ImageView = view.findViewById(R.id.memberImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = members[position]
        holder.memberUsername.text = member.username

        Glide.with(holder.itemView.context)
            .load(member.images.jpg.image_url)
            .into(holder.memberImage)
    }

    override fun getItemCount() = members.size

    fun updateMembers(newMembers: List<Data>) {
        members = newMembers
        notifyDataSetChanged()
    }
}
