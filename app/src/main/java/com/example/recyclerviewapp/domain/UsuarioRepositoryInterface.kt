package com.example.recyclerviewapp.domain

import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

interface UsuarioRepositoryInterface {

    suspend fun fetchUsers(): Flow<List<Usuario>>
    suspend fun fecthUserById(id: Int): Flow<UsuarioDetails?>
    suspend fun refreshUsuarios()

    suspend fun refreshUsuariosById(id: Int)

    suspend fun insert(usuario: UsuarioEntity)

    suspend fun refreshUsuariosSincronizado()
}