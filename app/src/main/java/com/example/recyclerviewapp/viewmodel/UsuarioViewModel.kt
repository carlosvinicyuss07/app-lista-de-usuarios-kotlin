package com.example.recyclerviewapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewapp.model.Usuario
import com.example.recyclerviewapp.network.ApiService
import com.example.recyclerviewapp.repository.UsuarioRepository
import kotlinx.coroutines.launch

class UsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> = _usuarios

    private val _erro = SingleLiveEvent<String>()
    val erro: LiveData<String> = _erro

    fun carregarUsuarios() {
        viewModelScope.launch {
            try {
                val lista = repository.fetchUsers()
                _usuarios.value = lista
            } catch (e: Exception) {
                _erro.value = "Falha: ${e.message}"
            }
        }
    }

}