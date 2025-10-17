package com.example.recyclerviewapp.data.local

import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.domain.Usuario
import com.example.recyclerviewapp.domain.UsuarioDetails

fun UsuarioEntity.toDomain() = Usuario(
    id = localId,
    nomeCompleto = name,
    email = email,
    origemLocal = origemLocal
)

fun UsuarioEntity.toDetailsDomain() = UsuarioDetails(
    id = localId,
    fullName = name,
    contact = "$email | $phone",
    website = website.toString(),
    address = address.toString(),
    geo = "",
    company = company.toString(),
    origemLocal = origemLocal
)