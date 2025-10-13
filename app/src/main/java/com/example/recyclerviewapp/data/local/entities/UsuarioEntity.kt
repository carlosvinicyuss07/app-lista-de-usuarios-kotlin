package com.example.recyclerviewapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsuarioEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)