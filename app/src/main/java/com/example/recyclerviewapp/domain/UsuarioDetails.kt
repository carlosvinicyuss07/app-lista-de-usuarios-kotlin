package com.example.recyclerviewapp.domain

data class UsuarioDetails(
    val id: Int,
    val fullName: String,
    val email: String,
    val phone: String,
    val website: String,
    val address: String,
    val geo: String,
    val company: String,
    val origemLocal: Boolean,
    val photoUri: String?
)
