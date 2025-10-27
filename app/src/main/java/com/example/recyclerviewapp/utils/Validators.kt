package com.example.recyclerviewapp.utils

import android.util.Patterns

fun validateName(nome: String): String? {
    return if (nome.length < 3) {
        "Nome deve ter ao menos 3 caracteres"
    } else if (nome.length > 50) {
        "Máximo 50 caracteres"
    } else {
        null
    }
}

fun validateEmail(email: String): String? {
    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return null
    }
    else {
        return "Email inválido"
    }
}

fun validatePhone(phone: String): String? {
    val tamanhoMinSemDDD = 9
    val tamanhoMaxComDDD = 12
    return if (phone.length < tamanhoMinSemDDD || phone.length > tamanhoMaxComDDD) {
        "Número de telefone inválido"
    } else {
        null
    }
}

fun validateStreet(street: String): String? {
    return if (street.length < 3) {
        "A rua deve ter ao menos 3 caracteres"
    } else if (street.length > 50) {
        "Máximo 50 caracteres"
    } else {
        null
    }
}

fun validateSuite(suite: String): String? {
    return if (suite.length < 3) {
        "O conjunto deve ter ao menos 3 caracteres"
    } else if (suite.length > 50) {
        "Máximo 50 caracteres"
    } else {
        null
    }
}

fun validateCity(city: String): String? {
    return if (city.length < 3) {
        "A rua deve ter ao menos 3 caracteres"
    } else if (city.length > 50) {
        "Máximo 50 caracteres"
    } else {
        null
    }
}

fun validateZipcode(zipcode: String): String? {
    val tamanhoCEP = 8
    return if (zipcode.length != tamanhoCEP) {
        "CEP inválido"
    } else {
        null
    }
}

fun validateWebsite(website: String): String? {
    return if (website.length < 3) {
        "Website deve ter ao menos 3 caracteres"
    } else if (website.length > 50) {
        "Máximo 50 caracteres"
    } else {
        null
    }
}

fun validateCompany(company: String): String? {
    return if (company.isEmpty()) {
        "Campo obrigatório"
    } else if (company.length > 30) {
        "Máximo 30 caracteres"
    } else {
        null
    }
}

fun validatePhotoUri(photoUri: String): String? {
    return if (photoUri.isBlank()) {
        "Origem da foto não encontrada"
    } else {
        null
    }
}

