package com.example.recyclerviewapp.ui

import androidx.annotation.ColorInt

data class UsuarioUi(
    val id: Int,
    val name: String,
    val username: String,
    val emailVisivel: String,
    val origemLocal: Boolean,
    val photoUri: String?,
    val cor: Int
)