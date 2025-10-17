package com.example.recyclerviewapp.domain

import com.example.recyclerviewapp.ui.UsuarioDetailsUi

fun UsuarioDetails.toUi() = UsuarioDetailsUi(
    name = fullName,
    username = fullName.split(" ")[0],
    formatedContact = "Contact: $email | $phone",
    websiteLabel = "Website: $website",
    formatedAddress = address,
    formatedGeo = "Geo: $geo",
    formatedCompany = company,
    origemLocal = origemLocal
)
