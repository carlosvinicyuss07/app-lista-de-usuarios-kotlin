package com.example.recyclerviewapp.repository

import com.example.recyclerviewapp.model.Usuario
import com.example.recyclerviewapp.model.UsuarioDetails
import com.example.recyclerviewapp.network.ApiService

class UsuarioRepository(private val api: ApiService) : UsuarioRepositoryInterface {
    override suspend fun fetchUsers(): List<Usuario> {
        return api.getUsuarios()
    }

    override suspend fun fecthUserById(id: Int): UsuarioDetails {
        return api.getUsuarioPorId(id)
    }
}