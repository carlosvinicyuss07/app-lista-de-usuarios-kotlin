package com.example.recyclerviewapp.ui

data class UsuarioDetailsUi(
    val name: String,
    val username: String,
    val formatedContact: String,
    val websiteLabel: String,
    val formatedAddress: String,
    val formatedGeo: String,
    val formatedCompany: String,
    val origemLocal: Boolean,
    val photoUri: String?
)
