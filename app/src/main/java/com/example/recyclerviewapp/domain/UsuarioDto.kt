package com.example.recyclerviewapp.domain

data class UsuarioDto(
    val name: String,
    val email: String,
    val phone: String,
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val website: String,
    val company: String,
    val photoUri: String?
)
