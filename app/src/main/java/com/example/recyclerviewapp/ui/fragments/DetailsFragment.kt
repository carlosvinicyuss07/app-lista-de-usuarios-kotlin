package com.example.recyclerviewapp.ui.fragments

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.databinding.FragmentDetailsBinding
import com.example.recyclerviewapp.viewmodel.DetailsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue
import androidx.core.net.toUri

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

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Limpa o menu atual e reinfla o menu principal
                menu.clear()
                menuInflater.inflate(R.menu.top_app_bar_menu, menu)

                // Oculta o item de busca
                menu.findItem(R.id.action_search)?.isVisible = false
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val userId = args.userId
        viewModel.carregarUsuario(localId = userId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.usuario.collect { usuario ->
                usuario?.let {

                    if (it.origemLocal) {
                        binding.cardDetails.setCardBackgroundColor("#27E0F5".toColorInt())
                    } else {
                        binding.cardDetails.setCardBackgroundColor("#F5BB27".toColorInt())
                    }

                    usuario.photoUri?.let { uri ->
                        binding.imgProfile.setImageURI(uri.toUri())
                    } ?: binding.imgProfile.setImageResource(R.drawable.user_details_image)

                    binding.txtName.text = usuario.name
                    binding.txtUsername.text = usuario.username
                    binding.txtContact.text = usuario.formatedContact
                    binding.txtWebsite.text = usuario.websiteLabel

                    binding.txtAddress.text = usuario.formatedAddress
                    binding.txtGeo.text = usuario.formatedGeo
                    binding.txtCompany.text = usuario.formatedCompany
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loading.collect { loading ->
                binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}