package com.gualoto.pfinaldm.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG
import com.gualoto.pfinaldm.databinding.UsersItemsBinding

class UsersAdapter(private val onDeleteItem: (Int) -> Unit,
                   private val onSelectItem: (FullInfoAnimeLG)->Unit )
: RecyclerView.Adapter<UsersAdapter.UsersVH>() {
    var listUsers: List<FullInfoAnimeLG> = listOf()

    class UsersVH(view: View) : RecyclerView.ViewHolder(view) {

        private var binding: UsersItemsBinding = UsersItemsBinding.bind(view)

        fun render(item: FullInfoAnimeLG,
                   OnDeleteItem: (Int) -> Unit,
                   onSelectItem: (FullInfoAnimeLG)->Unit) {
            binding.UserName.text = item.name
            binding.UserDesc.text = item.sypnosis
            binding.imgUser.load(item.big_image)
            binding.btnVer.setOnClickListener {
                onSelectItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersVH {
        val inflater = LayoutInflater.from(parent.context)
        return UsersVH(inflater.inflate(R.layout.users_items, parent, false))
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: UsersVH, position: Int) {
        holder.render(listUsers[position], onDeleteItem,onSelectItem)
    }
}