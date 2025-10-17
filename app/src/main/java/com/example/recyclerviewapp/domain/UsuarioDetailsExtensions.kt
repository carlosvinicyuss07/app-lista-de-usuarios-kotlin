package com.example.recyclerviewapp.domain

import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.ui.UsuarioDetailsUi

fun UsuarioDetails.toUi() = UsuarioDetailsUi(
    name = fullName,
    username = fullName.split(" ")[0],
    formatedContact = "Contact: $contact",
    websiteLabel = "Website: $website",
    formatedAddress = address,
    formatedGeo = geo,
    formatedCompany = company
)

fun UsuarioDetails.toEntity() = UsuarioEntity(
    idApi = id,
    name = fullName,
    username = fullName.split(" ")[0],
    email = contact.split(" | ")[0],
    phone = contact.split(" | ")[1],
    website = website,
    address = address,
    company = company,
    origemLocal = false
)