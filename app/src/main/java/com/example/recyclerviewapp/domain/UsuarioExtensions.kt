package com.example.recyclerviewapp.domain

import androidx.core.graphics.toColorInt
import com.example.recyclerviewapp.ui.UsuarioUi

fun Usuario.toUi() = UsuarioUi(
    id = id,
    name = fullname,
    username = fullname.split(" ")[0],
    emailVisivel = email,
    origemLocal = origemLocal,
    photoUri = photoUri,
    cor = if (origemLocal) {
        "#27E0F5".toColorInt()
    } else {
        "#F5BB27".toColorInt()
    }
)
