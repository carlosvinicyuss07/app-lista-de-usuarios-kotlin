package com.example.recyclerviewapp.data.network

import com.example.recyclerviewapp.domain.Usuario
import com.example.recyclerviewapp.domain.UsuarioDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users") // endpoint
    suspend fun getUsuarios(): List<Usuario>

    @GET("users/{id}")
    suspend fun getUsuarioPorId(@Path("id") id: Int): UsuarioDetails
}
