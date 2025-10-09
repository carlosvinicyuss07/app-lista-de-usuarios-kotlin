package com.example.recyclerviewapp.domain

interface UsuarioRepositoryInterface {

    suspend fun fetchUsers(): List<Usuario>
    suspend fun fecthUserById(id: Int): UsuarioDetails
    suspend fun refreshUsuarios()
}