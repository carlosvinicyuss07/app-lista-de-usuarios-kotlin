package com.example.recyclerviewapp.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.databinding.FragmentDetailsBinding
import com.example.recyclerviewapp.databinding.ItemUsuarioBinding
import com.example.recyclerviewapp.ui.UsuarioUi
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri

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
        val usuario: UsuarioUi = getItem(position)
        holder.bind(usuario)
    }

    inner class UsuarioViewHolder(private val binding: ItemUsuarioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(usuario: UsuarioUi) {

            binding.cardView.setCardBackgroundColor(usuario.cor)

            if (usuario.origemLocal && !usuario.photoUri.isNullOrEmpty()) {
                val uri = usuario.photoUri.toUri()
                binding.imgProfile.setImageURI(uri)
            } else {
                binding.imgProfile.setImageResource(R.drawable.user_details_image)
            }

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