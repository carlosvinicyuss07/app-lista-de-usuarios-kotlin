package com.example.recyclerviewapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.domain.UsuarioRepositoryInterface
import com.example.recyclerviewapp.domain.toUi
import com.example.recyclerviewapp.ui.UsuarioUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(
    private val repository: UsuarioRepositoryInterface
) : ViewModel() {

    private val _usuarios = MutableStateFlow<List<UsuarioUi>>(emptyList())
    val usuarios: StateFlow<List<UsuarioUi>> = _usuarios

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _status = MutableLiveData<UsuarioStatus>()
    val status: LiveData<UsuarioStatus> = _status

    private val _erro = MutableSharedFlow<String>()
    val erro: SharedFlow<String> = _erro

    fun atualizarUsuariosRemotos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.refreshUsuarios()
                repository.fetchUsers().collect { lista ->
                    _usuarios.value = lista.map { usuario ->
                        usuario.toUi()
                    }
                    _isLoading.value = false // encerra loading após a primeira emissão
                }
            } catch (e: Exception) {
                _erro.emit("Falha ao carregar usuários: ${e.message}")
            }
        }
    }

    fun adicionarUsuarioLocal(usuario: UsuarioEntity) {
        viewModelScope.launch {
            try {
                repository.insert(usuario.copy(origemLocal = true))
                _status.postValue(UsuarioStatus.Sucesso)
            } catch (e: Exception) {
                _status.postValue(UsuarioStatus.Erro(e.message ?: "Erro ao adicionarUsuarioLocal usuário"))
            }
        }
    }

    sealed class UsuarioStatus {
        object Sucesso : UsuarioStatus()
        data class Erro(val mensagem: String) : UsuarioStatus()
    }

}