package com.example.recyclerviewapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.databinding.ItemUsuarioBinding
import com.example.recyclerviewapp.ui.UsuarioUi

class UsuarioAdapter(
    private val onItemClick: (UsuarioUi) -> Unit
) : ListAdapter<UsuarioUi, UsuarioAdapter.UsuarioViewHolder>(
    DiffUtil
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UsuarioViewHolder(private val binding: ItemUsuarioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(usuario: UsuarioUi) {
            binding.tvName.text = usuario.name
            binding.tvUsername.text = "Username: ${usuario.username}"
            binding.tvEmail.text = "Email: ${usuario.emailVisivel}"

            binding.root.setOnClickListener {
                onItemClick(usuario)
            }
        }
    }

    object DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<UsuarioUi>() {
        override fun areItemsTheSame(oldItem: UsuarioUi, newItem: UsuarioUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UsuarioUi, newItem: UsuarioUi): Boolean {
            return oldItem == newItem
        }
    }
}