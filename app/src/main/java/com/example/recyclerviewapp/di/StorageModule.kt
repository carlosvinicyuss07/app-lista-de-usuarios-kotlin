package com.example.recyclerviewapp.di

import androidx.room.Room
import com.example.recyclerviewapp.MIGRATION_1_2
import com.example.recyclerviewapp.MIGRATION_2_3
import com.example.recyclerviewapp.data.UsuarioRepository
import com.example.recyclerviewapp.data.local.ListaDeUsuariosDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val storageModule = module {
    singleOf(::UsuarioRepository)
    single {
        Room.databaseBuilder(
            androidContext(),
            ListaDeUsuariosDatabase::class.java, "lista-de-usuarios.db"
        ).addMigrations(MIGRATION_2_3)
            .build()
    }
    single {
        get<ListaDeUsuariosDatabase>().usuarioDao()
    }
}