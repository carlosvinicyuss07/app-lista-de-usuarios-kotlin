package com.example.recyclerviewapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewapp.domain.UsuarioRepositoryInterface
import com.example.recyclerviewapp.domain.toUi
import com.example.recyclerviewapp.ui.UsuarioUi
import kotlinx.coroutines.launch

class UsuarioViewModel(private val repository: UsuarioRepositoryInterface) : ViewModel() {

    private val _usuarios = MutableLiveData<List<UsuarioUi>>()
    val usuarios: LiveData<List<UsuarioUi>> = _usuarios

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
                _usuarios.value = lista.map { usuario ->
                    usuario.toUi()
                }

            } catch (e: Exception) {
                _erro.value = "Falha: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

}