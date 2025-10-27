package com.example.recyclerviewapp.data

import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.data.network.UsuarioResourceOriginal

fun UsuarioResourceOriginal.toEntity(localId: Int?) = UsuarioEntity(
    localId = localId ?: 0,
    idApi = id,
    name = name,
    username = username,
    email = email,
    street = address.street,
    suite = address.suite,
    city = address.city,
    zipcode = address.zipcode,
    lat = address.geo.lat,
    lng = address.geo.lng,
    phone = phone,
    website = website,
    company = company.name
)