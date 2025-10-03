package com.example.recyclerviewapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

    private val args: DetailsFragmentArgs by navArgs() // Safe Args configurado

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

        val userId = args.userId
        viewModel.carregarUsuario(userId)

        viewModel.usuario.observe(viewLifecycleOwner) { usuario ->
            usuario?.let {
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
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}