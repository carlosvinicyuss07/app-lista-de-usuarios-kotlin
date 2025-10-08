package com.example.recyclerviewapp.repository

import com.example.recyclerviewapp.model.Usuario
import com.example.recyclerviewapp.model.UsuarioDetails
import com.example.recyclerviewapp.network.ApiService

class UsuarioRepository(private val api: ApiService) : UsuarioRepositoryInterface {

    private var cachedUsuarios: List<Usuario>? = null

    override suspend fun fetchUsers(): List<Usuario> {
        if (cachedUsuarios != null) {
            return cachedUsuarios!!
        }
        val usuarios = api.getUsuarios()
        cachedUsuarios = usuarios
        return usuarios
    }

    override suspend fun fecthUserById(id: Int): UsuarioDetails {
        return api.getUsuarioPorId(id)
    }

    override suspend fun refreshUsuarios() {
        val usuarios = api.getUsuarios()
        cachedUsuarios = usuarios
    }
}