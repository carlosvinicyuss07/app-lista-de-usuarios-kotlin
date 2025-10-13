package com.example.recyclerviewapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewapp.domain.UsuarioRepositoryInterface
import com.example.recyclerviewapp.domain.toUi
import com.example.recyclerviewapp.ui.UsuarioDetailsUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: UsuarioRepositoryInterface) : ViewModel() {

    private val _usuario = MutableStateFlow<UsuarioDetailsUi?>(null)
    val usuario: StateFlow<UsuarioDetailsUi?> get() = _usuario

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _erro = MutableSharedFlow<String>()
    val erro: SharedFlow<String> get() = _erro

    fun carregarUsuario(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val usuarioDetalhado = repository.fecthUserById(id).toUi()
                _usuario.value = usuarioDetalhado
            } catch (e: Exception) {
                _erro.emit("Falha ao carregar usuário: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

}