package com.example.recyclerviewapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.databinding.FragmentFormUsuarioBinding
import com.example.recyclerviewapp.viewmodel.UsuarioViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FormUsuarioFragment : Fragment() {

    private var _binding: FragmentFormUsuarioBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UsuarioViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        observeStatus()
    }

    private fun setupListeners() {
        binding.btnSalvar.setOnClickListener {
            val nome = binding.edtNome.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty()) {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Aqui marcamos que é um usuário local
            val usuario = UsuarioEntity(
                name = nome,
                email = email,
                username = "",
                street = "",
                suite = "",
                city = "",
                zipcode = "",
                lat = "",
                lng = "",
                phone = "",
                website = "",
                company = "",
                origemLocal = true,
            )

            viewModel.adicionarUsuarioLocal(usuario)
        }
    }

    private fun observeStatus() {
        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                is UsuarioViewModel.UsuarioStatus.Sucesso -> {
                    Toast.makeText(requireContext(), "Usuário cadastrado!", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                is UsuarioViewModel.UsuarioStatus.Erro -> {
                    Toast.makeText(requireContext(), status.mensagem, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}