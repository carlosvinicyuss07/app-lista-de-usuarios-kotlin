package com.example.recyclerviewapp.domain

import com.example.recyclerviewapp.ui.UsuarioUi

fun Usuario.toUi() = UsuarioUi(
    id = id,
    name = nomeCompleto,
    username = nomeCompleto.split(" ")[0],
    emailVisivel = email,
    origemLocal = origemLocal
)
