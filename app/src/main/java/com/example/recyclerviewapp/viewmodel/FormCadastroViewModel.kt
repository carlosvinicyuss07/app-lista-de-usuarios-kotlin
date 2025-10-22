package com.example.recyclerviewapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.domain.UsuarioRepositoryInterface
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FormCadastroViewModel(
    private val repository: UsuarioRepositoryInterface
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _status = MutableLiveData<UsuarioStatus>()
    val status: LiveData<UsuarioStatus> = _status

    private val _erro = MutableSharedFlow<String>()
    val erro: SharedFlow<String> = _erro

    fun adicionarUsuarioLocal(usuario: UsuarioEntity) {
        viewModelScope.launch {
            try {
                repository.insert(usuario.copy(origemLocal = true))
                _status.postValue(UsuarioStatus.Sucesso)
            } catch (e: Exception) {
                _status.postValue(
                    UsuarioStatus.Erro(
                        e.message ?: "Erro ao adicionarUsuarioLocal usuário"
                    )
                )
            }
        }
    }

    sealed class UsuarioStatus {
        object Sucesso : UsuarioStatus()
        data class Erro(val mensagem: String) : UsuarioStatus()
    }

}