package com.example.recyclerviewapp.ui.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.databinding.FragmentFormUsuarioBinding
import com.example.recyclerviewapp.viewmodel.FormCadastroViewModel
import com.example.recyclerviewapp.viewmodel.FormEffect
import com.example.recyclerviewapp.viewmodel.FormIntent
import com.example.recyclerviewapp.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import androidx.core.net.toUri
import com.example.recyclerviewapp.R

class FormUsuarioFragment : Fragment() {

    private var _binding: FragmentFormUsuarioBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    private val viewModel: FormCadastroViewModel by viewModel()

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            viewModel.process(FormIntent.PhotoUriChanged(value = imageUri.toString()))
        }
    }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) openCamera()
        else Toast.makeText(requireContext(), "Permissão de câmera negada", Toast.LENGTH_SHORT).show()
    }

    private val photoPickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.process(FormIntent.PhotoUriChanged(value = uri.toString()))
        } else {
            Toast.makeText(requireContext(), "Nenhuma imagem selecionada", Toast.LENGTH_SHORT).show()
        }
    }

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

        binding.btnAddPhoto.setOnClickListener {
            showImageOptionsDialog()
        }

        setupListeners()
        observeStatus()
        observeEffects()
    }

    private fun setupListeners() {
        binding.edtNome.doOnTextChanged { text, _, _, _ ->
            viewModel.process(FormIntent.NomeChanged(text.toString()))
        }
        binding.edtEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.process(FormIntent.EmailChanged(text.toString()))
        }
        binding.edtStreet.doOnTextChanged { text, _, _, _ ->
            viewModel.process(FormIntent.StreetChanged(text.toString()))
        }
        binding.edtSuite.doOnTextChanged { text, _, _, _ ->
            viewModel.process(FormIntent.SuiteChanged(text.toString()))
        }
        binding.edtCity.doOnTextChanged { text, _, _, _ ->
            viewModel.process(FormIntent.CityChanged(text.toString()))
        }
        binding.edtZipcode.doOnTextChanged { text, _, _, _ ->
            viewModel.process(FormIntent.ZipcodeChanged(text.toString()))
        }
        binding.edtPhone.doOnTextChanged { text, _, _, _ ->
            viewModel.process(FormIntent.PhoneChanged(text.toString()))
        }
        binding.edtWebsite.doOnTextChanged { text, _, _, _ ->
            viewModel.process(FormIntent.WebsiteChanged(text.toString()))
        }
        binding.edtCompany.doOnTextChanged { text, _, _, _ ->
            viewModel.process(FormIntent.CompanyChanged(text.toString()))
        }

        binding.btnSalvar.setOnClickListener {
            viewModel.process(FormIntent.Submit)
        }
    }

    private fun observeStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.edtNome.setTextIfDifferent(state.nome.value)
                    binding.edtNome.error = state.nome.error
                    binding.edtEmail.setTextIfDifferent(state.email.value)
                    binding.edtEmail.error = state.email.error
                    binding.edtStreet.setTextIfDifferent(state.street.value)
                    binding.edtStreet.error = state.street.error
                    binding.edtSuite.setTextIfDifferent(state.suite.value)
                    binding.edtSuite.error = state.suite.error
                    binding.edtCity.setTextIfDifferent(state.city.value)
                    binding.edtCity.error = state.city.error
                    binding.edtZipcode.setTextIfDifferent(state.zipcode.value)
                    binding.edtZipcode.error = state.zipcode.error
                    binding.edtPhone.setTextIfDifferent(state.phone.value)
                    binding.edtPhone.error = state.phone.error
                    binding.edtWebsite.setTextIfDifferent(state.website.value)
                    binding.edtWebsite.error = state.website.error
                    binding.edtCompany.setTextIfDifferent(state.company.value)
                    binding.edtCompany.error = state.company.error

                    if (state.photoUri.value.isNotBlank()) {
                        val uri = state.photoUri.value.toUri()
                        binding.imgProfileForm.setImageURI(uri)
                    } else {
                        binding.imgProfileForm.setImageResource(R.drawable.user_details_image)
                    }

                    binding.btnSalvar.isEnabled = state.isSaveEnable

                }
            }
        }
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect { effect ->
                    when(effect) {
                        FormEffect.SuccessSaved -> {
                            Toast.makeText(requireContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                        is FormEffect.ShowMessage -> {
                            Toast.makeText(requireContext(), effect.text, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showImageOptionsDialog() {
        val options = arrayOf("Tirar foto", "Escolher da galeria")

        AlertDialog.Builder(requireContext())
            .setTitle("Selecionar foto de perfil")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> checkCameraPermissionAndOpen()
                    1 -> checkStoragePermissionAndOpen()
                }
            }
            .show()
    }

    private fun checkCameraPermissionAndOpen() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun checkStoragePermissionAndOpen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 (API 30) ou superior: usar Photo Picker
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        } else {
            // Android 10 ou inferior: usar seletor tradicional
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            legacyGalleryLauncher.launch(intent)
        }
    }

    private val legacyGalleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    binding.imgProfileForm.setImageURI(uri)
                    imageUri = uri
                }
            }
        }

    private fun openCamera() {
        val imageFile = File.createTempFile("profile_", ".jpg", requireContext().cacheDir)
        imageUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            imageFile
        )
        cameraLauncher.launch(imageUri)
    }

    fun EditText.setTextIfDifferent(value: String) {
        if (text?.toString() != value) setText(value)
    }
}