package com.example.recyclerviewapp.model

data class Pessoa(val cpf: String, val nome: String, val idade: Int) {

    override fun toString(): String {
        return "$nome - $idade anos"
    }
}
