package com.example.recyclerviewapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recyclerviewapp.databinding.FragmentDetailsBinding
import com.example.recyclerviewapp.network.RetrofitClient
import com.example.recyclerviewapp.repository.UsuarioRepository
import com.example.recyclerviewapp.viewmodel.DetailsViewModel
import com.example.recyclerviewapp.viewmodel.DetailsViewModelFactory
import kotlin.getValue

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(UsuarioRepository(RetrofitClient.instance))
    }

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getInt(ARG_USER_ID, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (userId != -1) {
            viewModel.carregarUsuario(userId)
        }

        viewModel.usuario.observe(viewLifecycleOwner) { usuario ->
            binding.txtNome.text = usuario.name
            binding.txtUsername.text = usuario.username
            binding.txtEmail.text = usuario.email
            binding.txtTelefone.text = usuario.phone
            binding.txtWebsite.text = usuario.website

            binding.txtEndereco.text =
                "${usuario.address.street}, ${usuario.address.suite}\n" +
                        "${usuario.address.city} - CEP: ${usuario.address.zipcode}"

            binding.txtGeo.text =
                "Lat: ${usuario.address.geo.lat}, Lng: ${usuario.address.geo.lng}"

            binding.txtEmpresa.text =
                "${usuario.company.name}\n" +
                        "${usuario.company.catchPhrase}\n" +
                        usuario.company.bs
        }

        binding.btnVoltar.setOnClickListener {
            parentFragmentManager.popBackStack() // Faz o retorno para a lista
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_USER_ID = "USER_ID"
        fun newInstance(userId: Int) = DetailsFragment().apply {
            arguments = Bundle().apply { putInt(ARG_USER_ID, userId) }
        }
    }

}