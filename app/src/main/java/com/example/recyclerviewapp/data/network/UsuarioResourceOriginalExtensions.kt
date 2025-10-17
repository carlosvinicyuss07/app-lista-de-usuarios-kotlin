package com.example.recyclerviewapp.data.network

fun UsuarioResourceOriginal.toNormalized(): UsuarioResource {
    return UsuarioResource(
        id = id,
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
}