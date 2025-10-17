package com.example.recyclerviewapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.recyclerviewapp.data.local.entities.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios")
    fun findAll(): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM usuarios")
    suspend fun findAllOnce(): List<UsuarioEntity>

    @Query("SELECT * FROM usuarios WHERE localId = :localId")
    fun findByLocalId(localId: Int): Flow<UsuarioEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(usuarios: List<UsuarioEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usuario: UsuarioEntity)

    @Query("DELETE FROM usuarios")
    suspend fun clear()

    @Update
    suspend fun update(usuario: UsuarioEntity)

    @Transaction
    suspend fun replaceAll(usuarios: List<UsuarioEntity>) {
        clear()
        insertAll(usuarios)
    }

}