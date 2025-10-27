package com.example.recyclerviewapp.viewmodel

import android.net.Uri

data class CadastroUsuarioState(
    val nome: FormField = FormField(),
    val email: FormField = FormField(),
    val street: FormField = FormField(),
    val suite: FormField = FormField(),
    val city: FormField = FormField(),
    val zipcode: FormField = FormField(),
    val phone: FormField = FormField(),
    val website: FormField = FormField(),
    val company: FormField = FormField(),
    val photoUri: FormField = FormField(),
    val isSaveEnable: Boolean = false,
    val isLoading: Boolean = false
)

data class FormField(
    val value: String = "",
    val error: String? = null
)

sealed class FormIntent {
    data class NomeChanged(val value: String) : FormIntent()
    data class EmailChanged(val value: String) : FormIntent()
    data class StreetChanged(val value: String) : FormIntent()
    data class SuiteChanged(val value: String) : FormIntent()
    data class CityChanged(val value: String) : FormIntent()
    data class ZipcodeChanged(val value: String) : FormIntent()
    data class PhoneChanged(val value: String) : FormIntent()
    data class WebsiteChanged(val value: String) : FormIntent()
    data class CompanyChanged(val value: String) : FormIntent()
    data class PhotoUriChanged(val value: String) : FormIntent()
    object Submit : FormIntent()
}

sealed class FormEffect {
    object SuccessSaved : FormEffect()
    data class ShowMessage(val text: String) : FormEffect()
}
