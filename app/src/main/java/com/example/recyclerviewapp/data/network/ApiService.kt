package com.example.recyclerviewapp.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users") // endpoint
    suspend fun getUsuarios(): List<UsuarioResourceOriginal>

    @GET("users/{id}")
    suspend fun getUsuarioPorId(@Path("id") id: Int): UsuarioResourceOriginal
}
