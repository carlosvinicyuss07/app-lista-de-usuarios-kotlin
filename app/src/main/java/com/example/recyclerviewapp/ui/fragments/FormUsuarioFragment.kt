package com.example.recyclerviewapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewapp.R
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

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setupListeners()
        observeStatus()
    }

    private fun setupListeners() {
        binding.btnSalvar.setOnClickListener {
            val nome = binding.edtNome.text.toString().trim()
            val email = binding.edtEmail.text.toString().trim()
            val street = binding.edtStreet.text.toString().trim()
            val suite = binding.edtSuite.text.toString().trim()
            val city = binding.edtCity.text.toString().trim()
            val zipcode = binding.edtZipcode.text.toString().trim()
            val phone = binding.edtPhone.text.toString().trim()
            val website = binding.edtWebsite.text.toString().trim()
            val company = binding.edtCompany.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty()) {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Aqui marcamos que é um usuário local
            val usuario = UsuarioEntity(
                name = nome,
                email = email,
                username = "",
                street = street,
                suite = suite,
                city = city,
                zipcode = zipcode,
                lat = "01010101",
                lng = "01010101",
                phone = phone,
                website = website,
                company = company,
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