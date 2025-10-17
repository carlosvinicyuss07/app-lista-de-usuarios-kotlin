package com.example.recyclerviewapp.data

import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.data.network.UsuarioDetailsResource
import com.example.recyclerviewapp.domain.UsuarioDetails

fun UsuarioDetailsResource.toEntity(localId: Int?) = UsuarioEntity(
    localId = localId ?: 0,
    idApi = id,
    name = name,
    username = username,
    email = email,
    phone = phone,
    website = website,
    address = "${address.street}, ${address.suite}\n" +
            "${address.city} - CEP: ${address.zipcode}" +
            "Lat: ${address.geo.lat}," + " Lng: ${address.geo.lng}",
    company = "${company.name}\n" +
            "${company.catchPhrase}\n" +
            company.bs,
    origemLocal = false
)