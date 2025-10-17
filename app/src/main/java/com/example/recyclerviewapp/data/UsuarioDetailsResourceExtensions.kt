package com.example.recyclerviewapp.data

import com.example.recyclerviewapp.data.network.UsuarioDetailsResource
import com.example.recyclerviewapp.domain.UsuarioDetails

fun UsuarioDetailsResource.toDomain() = UsuarioDetails(
    id = id,
    fullName = name,
    contact = "$email | $phone",
    website = website,
    address = "${address.street}, ${address.suite}\n" +
            "${address.city} - CEP: ${address.zipcode}",
    geo = "Lat: ${address.geo.lat}," + " Lng: ${address.geo.lng}",
    company = "${company.name}\n" +
            "${company.catchPhrase}\n" +
            company.bs,
    origemLocal = false
)