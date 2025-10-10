package com.example.recyclerviewapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.recyclerviewapp.databinding.FragmentDetailsBinding
import com.example.recyclerviewapp.viewmodel.DetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModel()

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
                binding.txtName.text = usuario.name
                binding.txtUsername.text = usuario.username
                binding.txtContact.text = usuario.formatedContact
                binding.txtWebsite.text = usuario.websiteLabel

                binding.txtAddress.text = usuario.formatedAddress
                binding.txtGeo.text = usuario.formatedGeo
                binding.txtCompany.text = usuario.formatedCompany
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