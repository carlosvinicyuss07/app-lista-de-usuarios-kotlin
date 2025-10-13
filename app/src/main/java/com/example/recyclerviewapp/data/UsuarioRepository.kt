package com.example.recyclerviewapp.data

import com.example.recyclerviewapp.data.local.dao.UsuarioDao
import com.example.recyclerviewapp.data.local.toDetailsDomain
import com.example.recyclerviewapp.data.local.toDomain
import com.example.recyclerviewapp.domain.Usuario
import com.example.recyclerviewapp.domain.UsuarioDetails
import com.example.recyclerviewapp.data.network.ApiService
import com.example.recyclerviewapp.domain.UsuarioRepositoryInterface
import com.example.recyclerviewapp.domain.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UsuarioRepository(
    private val api: ApiService,
    private val usuarioDao: UsuarioDao
) : UsuarioRepositoryInterface {

    private var cachedUsuarios: List<Usuario>? = null

    override suspend fun fetchUsers(): Flow<List<Usuario>> {

        return usuarioDao.findAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun fecthUserById(id: Int): Flow<UsuarioDetails?> {
        return usuarioDao.findById(id).map { entity ->
            entity?.toDetailsDomain()
        }
    }

    override suspend fun refreshUsuarios() = withContext(Dispatchers.IO) {
        try {
            val usuariosRemotos = api.getUsuarios().map { usuarioResource ->
                usuarioResource.toDomain()
            }
            usuarioDao.replaceAll(usuariosRemotos.map { it.toEntity() })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun refreshUsuariosById(id: Int) {
        try {
            val remote = api.getUsuarioPorId(id).toDomain()
            usuarioDao.insert(remote.toEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}