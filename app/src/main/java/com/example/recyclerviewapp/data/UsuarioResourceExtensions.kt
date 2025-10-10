package com.example.recyclerviewapp.data

import com.example.recyclerviewapp.data.network.UsuarioResource
import com.example.recyclerviewapp.domain.Usuario

fun UsuarioResource.toDomain() = Usuario(
    id = id,
    nomeCompleto = name,
    email = email
)