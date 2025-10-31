package com.example.recyclerviewapp.ui.fragments

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.recyclerviewapp.databinding.FragmentCameraBinding
import java.io.File

class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding
    private lateinit var cameraController: LifecycleCameraController

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) startCamera()
        else Toast.makeText(requireContext(), "Permissão negada", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Solicita permissão
        permissionLauncher.launch(Manifest.permission.CAMERA)

        binding.btnTakePhoto.setOnClickListener {
            takePhoto()
        }
    }

    private fun startCamera() {
        cameraController = LifecycleCameraController(requireContext())
        cameraController.bindToLifecycle(viewLifecycleOwner)
        binding.previewView.controller = cameraController
    }

    private fun takePhoto() {
        val outputDir = requireContext().cacheDir
        val photoFile = File(outputDir, "photo_${System.currentTimeMillis()}.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        cameraController.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val uri = Uri.fromFile(photoFile)
                    Toast.makeText(requireContext(), "Foto salva!", Toast.LENGTH_SHORT).show()

                    // Retorna o URI para o fragment anterior
                    setFragmentResult("camera_result", bundleOf("photoUri" to uri.toString()))
                    findNavController().popBackStack()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(requireContext(), "Erro: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }
}