package com.example.recyclerviewapp.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.databinding.ActivityMainBinding
import com.example.recyclerviewapp.network.RetrofitClient
import com.example.recyclerviewapp.repository.UsuarioRepository
import com.example.recyclerviewapp.ui.fragments.HomeFragment
import com.example.recyclerviewapp.viewmodel.UsuarioViewModel
import com.example.recyclerviewapp.viewmodel.UsuarioViewModelFactory

class MainActivity : AppCompatActivity() {

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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            return true
        }
        return super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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
}