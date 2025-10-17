package com.example.recyclerviewapp.data

import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.data.network.UsuarioResource

fun UsuarioResource.toEntity(localId: Int?) = UsuarioEntity(
    localId = localId ?: 0,
    idApi = id,
    name = name,
    username = username,
    email = email,
    street = street,
    suite = suite,
    city = city,
    zipcode = zipcode,
    lat = lat,
    lng = lng,
    phone = phone,
    website = website,
    company = company
)