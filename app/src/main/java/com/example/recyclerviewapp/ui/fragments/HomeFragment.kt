package com.example.recyclerviewapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewapp.databinding.FragmentHomeBinding
import com.example.recyclerviewapp.ui.adapters.UsuarioAdapter
import com.example.recyclerviewapp.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UsuarioViewModel by viewModel()

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.usuarios.collect { lista ->
                usuarioAdapter.submitList(lista)
            }
        }

        if (viewModel.usuarios.value.isEmpty()) {
            viewModel.atualizarUsuariosRemotos()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}