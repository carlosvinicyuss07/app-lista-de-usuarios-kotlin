package com.example.recyclerviewapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0, // <- ID interno do Room, sempre único

    val idApi: Int? = null, // <- ID vindo da API, se houver
    val name: String,
    val username: String,
    val email: String,
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val lat: String,
    val lng: String,
    val phone: String,
    val website: String,
    val company: String,
    val origemLocal: Boolean = false,
    val photoUri: String? = null
)