package com.example.recyclerviewapp.domain

import androidx.core.graphics.toColorInt
import com.example.recyclerviewapp.ui.UsuarioDetailsUi

fun UsuarioDetails.toUi() = UsuarioDetailsUi(
    name = fullName,
    username = fullName.split(" ")[0],
    formatedContact = "Contact: $email | $phone",
    websiteLabel = "Website: $website",
    formatedAddress = address,
    formatedGeo = "Geo: $geo",
    formatedCompany = company,
    origemLocal = origemLocal,
    photoUri = photoUri,
    cor = if (origemLocal) {
        "#27E0F5".toColorInt()
    } else {
        "#F5BB27".toColorInt()
    }
)
