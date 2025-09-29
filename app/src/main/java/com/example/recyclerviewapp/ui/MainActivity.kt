package com.example.recyclerviewapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewapp.databinding.ActivityMainBinding
import com.example.recyclerviewapp.viewmodel.UsuarioViewModelFactory
import com.example.recyclerviewapp.network.RetrofitClient
import com.example.recyclerviewapp.repository.UsuarioRepository
import com.example.recyclerviewapp.ui.adapters.UsuarioAdapter
import com.example.recyclerviewapp.viewmodel.UsuarioViewModel

class MainActivity : AppCompatActivity() {

    private val usuarioAdapter: UsuarioAdapter = UsuarioAdapter()
    private val viewModel: UsuarioViewModel by viewModels {
        val repository = UsuarioRepository(RetrofitClient.instance)
        UsuarioViewModelFactory(repository)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        setupView()

        binding.btnOpenDetails.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("USER_ID", 123)
            startActivity(intent)
        }

        viewModel.usuarios.observe(this) { lista ->
            usuarioAdapter.submitList(lista)
        }

        viewModel.erro.observe(this) { mensagem ->
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        }

        viewModel.carregarUsuarios()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            com.example.recyclerviewapp.R.id.action_search -> {
                // Botão "buscar"
                Toast.makeText(this, "Buscar clicado!", Toast.LENGTH_SHORT).show()
                true
            }
            com.example.recyclerviewapp.R.id.action_settings -> {
                // Botão "configurações"
                Toast.makeText(this, "Configurações clicado!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.recyclerviewapp.R.menu.top_app_bar_menu, menu)
        return true
    }

    private fun setupView() {
        binding.listaPessoas.adapter = usuarioAdapter
    }
}