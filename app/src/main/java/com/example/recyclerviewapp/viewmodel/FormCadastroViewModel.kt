package com.example.recyclerviewapp.viewmodel

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
                val error = validateName(intent.value)
                _state.update {
                    it.copy(
                        nome = it.nome.copy(
                            value = intent.value,
                            error = error
                        ),
                        isSaveEnable = validateAllFields(it.copy(
                            nome = it.nome.copy(value = intent.value, error = error)
                        ))
                    )
                }
            }
            is FormIntent.EmailChanged -> {
                viewModelScope.launch {
                    val email = intent.value.trim()
                    val formatError = validateEmail(email)

                    val duplicateError = if (repository.emailExists(email)) {
                        "Email já cadastrado"
                    } else null

                    val finalError = formatError ?: duplicateError
                    _state.update {
                        it.copy(
                            email = it.email.copy(
                                value = email,
                                error = finalError
                            ),
                            isSaveEnable = validateAllFields(it.copy(
                                email = it.email.copy(value = email, error = finalError)
                            ))
                        )
                    }
                }

            }
            is FormIntent.StreetChanged -> {
                val error = validateStreet(intent.value)
                _state.update {
                    it.copy(
                        street = it.street.copy(
                            value = intent.value,
                            error = error
                        ),
                        isSaveEnable = validateAllFields(it.copy(
                            street = it.street.copy(value = intent.value, error = error)
                        ))
                    )
                }
            }
            is FormIntent.SuiteChanged -> {
                val error = validateSuite(intent.value)
                _state.update {
                    it.copy(
                        suite = it.suite.copy(
                            value = intent.value,
                            error = error
                        ),
                        isSaveEnable = validateAllFields(it.copy(
                            suite = it.suite.copy(value = intent.value, error = error)
                        ))
                    )
                }
            }
            is FormIntent.CityChanged -> {
                val error = validateCity(intent.value)
                _state.update {
                    it.copy(
                        city = it.city.copy(
                            value = intent.value,
                            error = error
                        ),
                        isSaveEnable = validateAllFields(it.copy(
                            city = it.city.copy(value = intent.value, error = error)
                        ))
                    )
                }
            }
            is FormIntent.ZipcodeChanged -> {
                val error = validateZipcode(intent.value)
                _state.update {
                    it.copy(
                        zipcode = it.zipcode.copy(
                            value = intent.value,
                            error = error
                        ),
                        isSaveEnable = validateAllFields(it.copy(
                            zipcode = it.zipcode.copy(value = intent.value, error = error)
                        ))
                    )
                }
            }
            is FormIntent.PhoneChanged -> {
                val error = validatePhone(intent.value)
                _state.update {
                    it.copy(
                        phone = it.phone.copy(
                            value = intent.value,
                            error = error
                        ),
                        isSaveEnable = validateAllFields(it.copy(
                            phone = it.phone.copy(value = intent.value, error = error)
                        ))
                    )
                }
            }
            is FormIntent.WebsiteChanged -> {
                val error = validateWebsite(intent.value)
                _state.update {
                    it.copy(
                        website = it.website.copy(
                            value = intent.value,
                            error = error
                        ),
                        isSaveEnable = validateAllFields(it.copy(
                            website = it.website.copy(value = intent.value, error = error)
                        ))
                    )
                }
            }
            is FormIntent.CompanyChanged -> {
                val error = validateCompany(intent.value)
                _state.update {
                    it.copy(
                        company = it.company.copy(
                            value = intent.value,
                            error = error
                        ),
                        isSaveEnable = validateAllFields(it.copy(
                            company = it.company.copy(value = intent.value, error = error)
                        ))
                    )
                }
            }
            is FormIntent.PhotoUriChanged -> {
                val error = validatePhotoUri(intent.value)
                _state.update {
                    it.copy(
                        photoUri = it.photoUri.copy(
                            value = intent.value,
                            error = error
                        ),
                        isSaveEnable = validateAllFields(it.copy(
                            photoUri = it.photoUri.copy(value = intent.value, error = error)
                        ))
                    )
                }
            }
            FormIntent.Submit -> adicionarUsuarioLocal()
        }
    }

    fun adicionarUsuarioLocal() {
        val current = _state.value
        if (!current.isSaveEnable) {
            viewModelScope.launch { _effect.emit(FormEffect.ShowMessage("Corrija os erros antes de salvar")) }
            return
        }

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
                    lat = "01010101",
                    lng = "01010101",
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

    private fun validateAllFields(state: CadastroUsuarioState): Boolean {
        return state.nome.error == null &&
                state.email.error == null &&
                state.street.error == null &&
                state.suite.error == null &&
                state.city.error == null &&
                state.zipcode.error == null &&
                state.phone.error == null &&
                state.website.error == null &&
                state.company.error == null &&
                state.nome.value.isNotBlank() &&
                state.email.value.isNotBlank() &&
                state.street.value.isNotBlank() &&
                state.suite.value.isNotBlank() &&
                state.city.value.isNotBlank() &&
                state.zipcode.value.isNotBlank() &&
                state.phone.value.isNotBlank() &&
                state.website.value.isNotBlank() &&
                state.company.value.isNotBlank()
    }
}