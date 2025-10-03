package com.example.recyclerviewapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewapp.databinding.FragmentHomeBinding
import com.example.recyclerviewapp.network.RetrofitClient
import com.example.recyclerviewapp.repository.UsuarioRepository
import com.example.recyclerviewapp.ui.adapters.UsuarioAdapter
import com.example.recyclerviewapp.viewmodel.UsuarioViewModel
import com.example.recyclerviewapp.viewmodel.UsuarioViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UsuarioViewModel by viewModels {
        UsuarioViewModelFactory(UsuarioRepository(RetrofitClient.instance))
    }

    private lateinit var usuarioAdapter: UsuarioAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usuarioAdapter = UsuarioAdapter { usuario ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(usuario.id)
            findNavController().navigate(action)
        }
        binding.listaPessoas.adapter = usuarioAdapter

        binding.listaPessoas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usuarioAdapter
        }

        viewModel.usuarios.observe(viewLifecycleOwner) { lista ->
            usuarioAdapter.submitList(lista)
        }

        if (viewModel.usuarios.value.isNullOrEmpty()) {
            viewModel.carregarUsuarios()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}