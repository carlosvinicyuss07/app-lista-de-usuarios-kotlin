package com.example.recyclerviewapp.data.local

import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.domain.Usuario
import com.example.recyclerviewapp.domain.UsuarioDetails

fun UsuarioEntity.toDomain() = Usuario(
    id = localId,
    fullname = name,
    email = email,
    origemLocal = origemLocal,
    photoUri = photoUri
)

fun UsuarioEntity.toDetailsDomain() = UsuarioDetails(
    id = localId,
    fullName = name,
    email = email,
    phone = phone,
    website = website,
    address = "$street, $suite\n" +
            "$city - CEP: $zipcode",
    geo = "lat: $lat | lng: $lng",
    company = company,
    origemLocal = origemLocal,
    photoUri = photoUri
)