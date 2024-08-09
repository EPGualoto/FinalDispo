package com.gualoto.pfinaldm.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.data.network.entities.anime.entities.FullInfoAnimeLG
import com.gualoto.pfinaldm.databinding.UsersItemsBinding

class UsersAdapterDiffUtil(
    private val onDeleteItem: (Int) -> Unit,
    private val onSelectItem: (FullInfoAnimeLG) -> Unit
) : ListAdapter<FullInfoAnimeLG, UsersAdapterDiffUtil.UsersVH>(DiffUtilUserCallBack) {

    class UsersVH(view: View) : RecyclerView.ViewHolder(view) {

        private var binding: UsersItemsBinding = UsersItemsBinding.bind(view)

        fun render(
            item: FullInfoAnimeLG,
            onSelectItem: (FullInfoAnimeLG) -> Unit
        ) {
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

    override fun onBindViewHolder(holder: UsersVH, position: Int) {
        holder.render(getItem(position), onSelectItem)
    }
}

private object DiffUtilUserCallBack : DiffUtil.ItemCallback<FullInfoAnimeLG>() {
    override fun areItemsTheSame(oldItem: FullInfoAnimeLG, newItem: FullInfoAnimeLG): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FullInfoAnimeLG, newItem: FullInfoAnimeLG): Boolean {
        return oldItem == newItem
    }
}
