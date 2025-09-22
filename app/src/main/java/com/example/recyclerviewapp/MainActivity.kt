package com.example.recyclerviewapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerviewapp.databinding.ActivityMainBinding
import com.example.recyclerviewapp.model.Pessoa

class MainActivity : AppCompatActivity() {

    private val pessoaAdapter: PessoaListAdapter = PessoaListAdapter()
    private lateinit var binding: ActivityMainBinding
    private val pessoas = listOf(
        Pessoa(nome = "Augusto", cpf = "4484028581", idade = 33),
        Pessoa(nome = "Augusto", cpf = "4484028581", idade = 33),
        Pessoa(nome = "Carlos", cpf = "4484028213", idade = 26),
        Pessoa(nome = "Carlos", cpf = "4484028212", idade = 26),
        Pessoa(nome = "Carlos", cpf = "4484028211", idade = 26),
        Pessoa(nome = "Carlos", cpf = "4484028133", idade = 26),
        Pessoa(nome = "Carlos", cpf = "448402854354", idade = 26),
        Pessoa(nome = "Carlos", cpf = "4484023453", idade = 26),
        Pessoa(nome = "Carlos", cpf = "4484234313", idade = 26),
        Pessoa(nome = "Carlos", cpf = "448403423413", idade = 26),
        Pessoa(nome = "Carlos", cpf = "4484023423413", idade = 26),
        Pessoa(nome = "Carlos", cpf = "4484543453", idade = 26),
        Pessoa(nome = "Carlos", cpf = "44843454313", idade = 46),
        Pessoa(nome = "Carlos", cpf = "44843453234513", idade = 19),
        Pessoa(nome = "Carlos", cpf = "4353454354553235", idade = 17),
        Pessoa(nome = "Carlos", cpf = "43534543511432235", idade = 17),
        Pessoa(nome = "Carlos", cpf = "4353457743543455", idade = 17),
        Pessoa(nome = "Carlos", cpf = "43534543548735", idade = 17),
        Pessoa(nome = "Carlos", cpf = "4353454878735435", idade = 17)
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        setupView()

        pessoaAdapter.submitList(pessoas)
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

    private fun setupView() {
        binding.listaPessoas.adapter = pessoaAdapter
    }
}