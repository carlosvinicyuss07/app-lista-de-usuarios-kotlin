package com.example.recyclerviewapp.repository

import com.example.recyclerviewapp.model.Usuario
import com.example.recyclerviewapp.model.UsuarioDetails

interface UsuarioRepositoryInterface {

    suspend fun fetchUsers(): List<Usuario>
    suspend fun fecthUserById(id: Int): UsuarioDetails
    suspend fun refreshUsuarios()
}