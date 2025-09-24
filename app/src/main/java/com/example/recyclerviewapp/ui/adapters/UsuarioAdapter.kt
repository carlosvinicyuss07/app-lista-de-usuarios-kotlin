package com.example.recyclerviewapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.databinding.ItemUsuarioBinding
import com.example.recyclerviewapp.model.Usuario

class UsuarioAdapter : ListAdapter<Usuario, UsuarioAdapter.UsuarioViewHolder>(
    DiffUtil
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UsuarioViewHolder(private val binding: ItemUsuarioBinding) :
        RecyclerView.ViewHolder(binding.root) {


        @SuppressLint("SetTextI18n")
        fun bind(usuario: Usuario) {
            binding.tvName.text = usuario.name
            binding.tvUsername.text = "Username: ${usuario.username}"
            binding.tvEmail.text = "Email: ${usuario.email}"
        }
    }

    object DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<Usuario>() {
        override fun areItemsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem == newItem
        }
    }
}