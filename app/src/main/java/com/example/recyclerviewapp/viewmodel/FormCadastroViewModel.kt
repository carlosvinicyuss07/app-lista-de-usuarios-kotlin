package com.example.recyclerviewapp.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.domain.UsuarioRepositoryInterface
import com.example.recyclerviewapp.utils.validateCity
import com.example.recyclerviewapp.utils.validateCompany
import com.example.recyclerviewapp.utils.validateEmail
import com.example.recyclerviewapp.utils.validateName
import com.example.recyclerviewapp.utils.validatePhone
import com.example.recyclerviewapp.utils.validatePhotoUri
import com.example.recyclerviewapp.utils.validateStreet
import com.example.recyclerviewapp.utils.validateSuite
import com.example.recyclerviewapp.utils.validateWebsite
import com.example.recyclerviewapp.utils.validateZipcode
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FormCadastroViewModel(
    private val repository: UsuarioRepositoryInterface
) : ViewModel() {

    private val _state = MutableStateFlow(CadastroUsuarioState())
    val state: StateFlow<CadastroUsuarioState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<FormEffect>()
    val effect: SharedFlow<FormEffect> = _effect.asSharedFlow()

    fun process(intent: FormIntent) {
        when (intent) {
            is FormIntent.NomeChanged -> {
                _state.update {
                    it.copy(
                        nome = it.nome.copy(
                            value = intent.value,
                            error = null
                        )
                    )
                }
            }
            is FormIntent.EmailChanged -> {
                viewModelScope.launch {
                    val email = intent.value.trim()
                    _state.update {
                        it.copy(
                            email = it.email.copy(
                                value = email,
                                error = null
                            )
                        )
                    }
                }
            }
            is FormIntent.StreetChanged -> {
                _state.update {
                    it.copy(
                        street = it.street.copy(
                            value = intent.value,
                            error = null
                        )
                    )
                }
            }
            is FormIntent.SuiteChanged -> {
                _state.update {
                    it.copy(
                        suite = it.suite.copy(
                            value = intent.value,
                            error = null
                        )
                    )
                }
            }
            is FormIntent.CityChanged -> {
                _state.update {
                    it.copy(
                        city = it.city.copy(
                            value = intent.value,
                            error = null
                        )
                    )
                }
            }
            is FormIntent.ZipcodeChanged -> {
                _state.update {
                    it.copy(
                        zipcode = it.zipcode.copy(
                            value = intent.value,
                            error = null
                        )
                    )
                }
            }
            is FormIntent.PhoneChanged -> {
                _state.update {
                    it.copy(
                        phone = it.phone.copy(
                            value = intent.value,
                            error = null
                        )
                    )
                }
            }
            is FormIntent.WebsiteChanged -> {
                _state.update {
                    it.copy(
                        website = it.website.copy(
                            value = intent.value,
                            error = null
                        )
                    )
                }
            }
            is FormIntent.CompanyChanged -> {
                _state.update {
                    it.copy(
                        company = it.company.copy(
                            value = intent.value,
                            error = null
                        )
                    )
                }
            }
            is FormIntent.PhotoUriChanged -> {
                _state.update {
                    it.copy(
                        photoUri = it.photoUri.copy(
                            value = intent.value,
                            error = null
                        )
                    )
                }
            }
            FormIntent.Submit -> adicionarUsuarioLocal()
        }
    }

    private fun validateForm() {
        val currentState = _state.value
        _state.update {
            it.copy(
                nome = it.nome.copy(
                    error = validateName(currentState.nome.value)
                ),
                email = it.email.copy(
                    error = validateEmail(currentState.email.value)
                ),
                phone = it.phone.copy(
                    error = validatePhone(currentState.phone.value)
                ),
                street = it.street.copy(
                    error = validateStreet(currentState.street.value)
                ),
                suite = it.suite.copy(
                    error = validateSuite(currentState.suite.value)
                ),
                city = it.city.copy(
                    error = validateCity(currentState.city.value)
                ),
                zipcode = it.zipcode.copy(
                    error = validateZipcode(currentState.zipcode.value)
                ),
                website = it.website.copy(
                    error = validateWebsite(currentState.website.value)
                ),
                company = it.company.copy(
                    error = validateCompany(currentState.company.value)
                ),
                photoUri = it.photoUri.copy(
                    error = validatePhotoUri(currentState.photoUri.value)
                )
            )
        }
    }

    private fun formIsValid(): Boolean {
        val currentState = _state.value
        val fields = listOf(
            currentState.nome,
            currentState.email,
            currentState.phone,
            currentState.street,
            currentState.suite,
            currentState.city,
            currentState.zipcode,
            currentState.website,
            currentState.company,
            currentState.photoUri
        )

        return fields.all {
            it.error == null
        }
    }

    fun adicionarUsuarioLocal() {
        validateForm()
        if (!formIsValid()) {
            viewModelScope.launch { _effect.emit(FormEffect.ShowMessage("Corrija os erros antes de salvar")) }
            return
        }

        val current = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val emailExists = repository.emailExists(current.email.value)
                if (emailExists) {
                    _state.update {
                        it.copy(
                            email = it.email.copy(error = "Email já cadastrado"),
                            isLoading = false
                        )
                    }
                    return@launch
                }

                val usuario = UsuarioEntity(
                    name = current.nome.value,
                    email = current.email.value,
                    username = "",
                    street = current.street.value,
                    suite = current.suite.value,
                    city = current.city.value,
                    zipcode = current.zipcode.value,
                    lat = "",
                    lng = "",
                    phone = current.phone.value,
                    website = current.website.value,
                    company = current.company.value,
                    photoUri = current.photoUri.value.takeIf { it.isNotBlank() },
                    origemLocal = true
                )
                repository.insert(usuario)
                _effect.emit(FormEffect.SuccessSaved)
            } catch (e: Exception) {
                _effect.emit(FormEffect.ShowMessage("Erro ao salvar: ${e.message}"))
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onPhotoSelected(uri: Uri) {
        _state.update { it.copy(photoUri = it.photoUri.copy(
            value = uri.toString()
        )) }
    }

}
