package com.example.recyclerviewapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewapp.model.UsuarioDetails
import com.example.recyclerviewapp.repository.UsuarioRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: UsuarioRepository) : ViewModel() {

    private val _usuario = MutableLiveData<UsuarioDetails>()
    val usuario: LiveData<UsuarioDetails> get() = _usuario

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _erro = SingleLiveEvent<String>()
    val erro: LiveData<String> get() = _erro

    fun carregarUsuario(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val usuarioDetalhado = repository.fecthUserById(id)
                _usuario.value = usuarioDetalhado
            } catch (e: Exception) {
                _erro.value = "Falha: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

}