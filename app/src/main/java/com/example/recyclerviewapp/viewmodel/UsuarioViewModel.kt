package com.example.recyclerviewapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewapp.model.Usuario
import com.example.recyclerviewapp.repository.UsuarioRepositoryInterface
import kotlinx.coroutines.launch

class UsuarioViewModel(private val repository: UsuarioRepositoryInterface) : ViewModel() {

    private val _usuarios = MutableLiveData<List<Usuario>>()
    val usuarios: LiveData<List<Usuario>> = _usuarios

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _erro = SingleLiveEvent<String>()
    val erro: LiveData<String> = _erro

    fun carregarUsuarios(forceReload: Boolean = false) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                if (forceReload) {
                    repository.refreshUsuarios()
                }

                val lista = repository.fetchUsers()
                _usuarios.value = lista

            } catch (e: Exception) {
                _erro.value = "Falha: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

}