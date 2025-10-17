package com.example.recyclerviewapp.domain

import com.example.recyclerviewapp.ui.UsuarioDetailsUi

fun UsuarioDetails.toUi() = UsuarioDetailsUi(
    name = fullName,
    username = fullName.split(" ")[0],
    formatedContact = "Contact: $contact",
    websiteLabel = "Website: $website",
    formatedAddress = address,
    formatedGeo = geo,
    formatedCompany = company,
    origemLocal = origemLocal
)
