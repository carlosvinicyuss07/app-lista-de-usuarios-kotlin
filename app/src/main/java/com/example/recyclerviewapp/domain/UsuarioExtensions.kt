package com.example.recyclerviewapp.domain

import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.ui.UsuarioUi

fun Usuario.toUi() = UsuarioUi(
    id = id,
    name = nomeCompleto,
    username = nomeCompleto.split(" ")[0],
    emailVisivel = email
)

fun Usuario.toEntity() = UsuarioEntity(
    id = id,
    name = nomeCompleto,
    username = nomeCompleto.split(" ")[0],
    email = email,
    address = "",
    phone = "",
    website = "",
    company = ""
)
