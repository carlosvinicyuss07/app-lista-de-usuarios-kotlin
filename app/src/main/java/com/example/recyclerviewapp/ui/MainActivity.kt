package com.example.recyclerviewapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewapp.databinding.ActivityMainBinding
import com.example.recyclerviewapp.model.Usuario
import com.example.recyclerviewapp.network.RetrofitClient
import com.example.recyclerviewapp.ui.adapters.UsuarioAdapter
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val usuarioAdapter: UsuarioAdapter = UsuarioAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        setupView()

        RetrofitClient.instance.getUsuarios().enqueue(object  : retrofit2.Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>?>, response: Response<List<Usuario>?>) {
                if (response.isSuccessful) {
                    val usuarios = response.body() ?: emptyList()
                    usuarioAdapter.submitList(usuarios)
                } else {
                    val codigo = response.code()
                    val erroBody = response.errorBody()?.string()
                }
            }

            override fun onFailure(call: Call<List<Usuario>?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Erro: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
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