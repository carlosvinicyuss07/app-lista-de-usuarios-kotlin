package com.example.recyclerviewapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recyclerviewapp.data.network.AddressResource
import com.example.recyclerviewapp.data.network.CompanyResource

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: String?,
    val phone: String?,
    val website: String?,
    val company: String?
)