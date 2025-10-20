package com.example.recyclerviewapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recyclerviewapp.data.local.dao.UsuarioDao
import com.example.recyclerviewapp.data.local.entities.UsuarioEntity

@Database(entities = [UsuarioEntity::class], version = 4)
abstract class ListaDeUsuariosDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
}