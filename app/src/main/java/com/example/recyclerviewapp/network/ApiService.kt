package com.example.recyclerviewapp.network

import com.example.recyclerviewapp.model.Usuario
import com.example.recyclerviewapp.model.UsuarioDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users") // endpoint
    suspend fun getUsuarios(): List<Usuario>

    @GET("users/{id}")
    suspend fun getUsuarioPorId(@Path("id") id: Int): UsuarioDetails
}
