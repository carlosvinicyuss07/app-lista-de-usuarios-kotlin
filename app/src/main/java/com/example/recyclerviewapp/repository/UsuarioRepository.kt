package com.example.recyclerviewapp.repository

import com.example.recyclerviewapp.model.Usuario
import com.example.recyclerviewapp.network.ApiService

class UsuarioRepository(private val api: ApiService) {
    suspend fun fetchUsers(): List<Usuario> {
        return api.getUsuarios()
    }
}