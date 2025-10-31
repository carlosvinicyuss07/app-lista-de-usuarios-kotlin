package com.example.recyclerviewapp.domain

import com.example.recyclerviewapp.data.local.entities.UsuarioEntity

fun UsuarioDto.toEntity() = UsuarioEntity(
    name = name,
    username = "",
    email = email,
    street = street,
    suite = suite,
    city = city,
    zipcode = zipcode,
    phone = phone,
    website = website,
    company = company,
    photoUri = photoUri,
    lat = "",
    lng = "",
    origemLocal = true
)