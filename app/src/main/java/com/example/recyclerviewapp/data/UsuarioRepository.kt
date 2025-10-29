package com.example.recyclerviewapp.data

import com.example.recyclerviewapp.data.local.dao.UsuarioDao
import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import com.example.recyclerviewapp.data.local.toDetailsDomain
import com.example.recyclerviewapp.data.local.toDomain
import com.example.recyclerviewapp.domain.Usuario
import com.example.recyclerviewapp.domain.UsuarioDetails
import com.example.recyclerviewapp.data.network.ApiService
import com.example.recyclerviewapp.domain.UsuarioRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UsuarioRepository(
    private val api: ApiService,
    private val usuarioDao: UsuarioDao
) : UsuarioRepositoryInterface {

    override suspend fun fetchUsers(): Flow<List<Usuario>> {

        return usuarioDao.findAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun fecthUserById(id: Int): Flow<UsuarioDetails?> {
        return usuarioDao.findByLocalId(id).map { entity ->
            entity?.toDetailsDomain()
        }
    }

    override suspend fun refreshUsuarios() = withContext(Dispatchers.IO) {
        try {
            val usuariosLocais = usuarioDao.findAllOnce()

            val usuariosRemotos = api.getUsuarios().map { usuarioResource ->
                usuarioResource.toEntity(
                    localId = usuariosLocais.firstOrNull { usuario ->
                        usuario.idApi == usuarioResource.id
                    }?.localId
                )
            }
            usuarioDao.insertAll(usuariosRemotos)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun refreshUsuariosById(id: Int): Unit = withContext(Dispatchers.IO) {
        try {
            val usuario = usuarioDao.find(id)
            usuario?.idApi?.let { idApi ->
                val remote = api.getUsuarioPorId(idApi).toEntity(localId = id)
                usuarioDao.insert(remote)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun insert(usuario: UsuarioEntity) = withContext(Dispatchers.IO) {
        usuarioDao.insert(usuario)
    }

    override suspend fun emailExists(email: String): Boolean {
        return usuarioDao.emailExists(email)
    }
}