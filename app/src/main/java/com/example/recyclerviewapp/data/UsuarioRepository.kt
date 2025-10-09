package com.example.recyclerviewapp.data

import com.example.recyclerviewapp.domain.Usuario
import com.example.recyclerviewapp.domain.UsuarioDetails
import com.example.recyclerviewapp.data.network.ApiService
import com.example.recyclerviewapp.domain.UsuarioRepositoryInterface

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