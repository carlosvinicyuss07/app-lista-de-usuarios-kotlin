package com.example.recyclerviewapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewapp.databinding.ItemPessoaBinding
import com.example.recyclerviewapp.model.Pessoa

class PessoaListAdapter : ListAdapter<Pessoa, PessoaListAdapter.PessoaListViewHolder>(
    DiffUtil
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PessoaListViewHolder {
        val binding = ItemPessoaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PessoaListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PessoaListViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class PessoaListViewHolder(private val binding: ItemPessoaBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(pessoa: Pessoa) {
            binding.tvPessoa.text = pessoa.toString()
        }
    }

    object DiffUtil : DiffUtil.ItemCallback<Pessoa>() {
        override fun areItemsTheSame(
            oldItem: Pessoa,
            newItem: Pessoa
        ): Boolean {
            return oldItem.cpf == newItem.cpf
        }

        override fun areContentsTheSame(
            oldItem: Pessoa,
            newItem: Pessoa
        ): Boolean {
            return oldItem == newItem
        }
    }

}