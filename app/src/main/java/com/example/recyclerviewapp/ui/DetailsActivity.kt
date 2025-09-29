package com.example.recyclerviewapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.databinding.ActivityDetailsBinding
import com.example.recyclerviewapp.network.RetrofitClient
import com.example.recyclerviewapp.repository.UsuarioRepository
import com.example.recyclerviewapp.viewmodel.DetailsViewModel
import com.example.recyclerviewapp.viewmodel.DetailsViewModelFactory

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(UsuarioRepository(RetrofitClient.instance))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        val userId = intent.getIntExtra("USER_ID", -1)

        if (userId != -1) {
            viewModel.carregarUsuario(userId)
        }

        observarViewModel()

        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observarViewModel() {
        viewModel.usuario.observe(this) { usuario ->
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

        viewModel.loading.observe(this) { loading ->
            binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.erro.observe(this) { erro ->
            if (erro != null) {
                Toast.makeText(this, erro, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            R.id.action_search -> {
                // Botão "buscar"
                Toast.makeText(this, "Buscar clicado!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                // Botão "configurações"
                Toast.makeText(this, "Configurações clicado!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        return true
    }
}
