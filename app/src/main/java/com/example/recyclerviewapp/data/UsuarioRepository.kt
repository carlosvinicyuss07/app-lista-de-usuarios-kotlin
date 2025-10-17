package com.example.recyclerviewapp.data

import android.util.Log
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

    override suspend fun refreshUsuariosById(id: Int) {
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

    override suspend fun insert(usuario: UsuarioEntity) {
        usuarioDao.insert(usuario)
    }

    override suspend fun refreshUsuariosSincronizado() {
        try {
            val usuariosApi = api.getUsuarios() // busca lista da API
            val usuariosLocais = usuarioDao.findAllOnce() // busca lista local sem Flow

            usuariosApi.forEach { usuarioApi ->

                val existente = usuariosLocais.find { it.idApi == usuarioApi.id }

                val usuarioEntity = UsuarioEntity(
                    localId = existente?.localId ?: 0,
                    idApi = usuarioApi.id,
                    name = usuarioApi.name,
                    username = usuarioApi.username,
                    email = usuarioApi.email,
                    origemLocal = false
                )

                if (existente != null) {
                    usuarioDao.update(usuarioEntity)
                } else {
                    usuarioDao.insert(usuarioEntity)
                }
            }

        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Erro ao sincronizar usuários", e)
            throw e
        }
    }
}