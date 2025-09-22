package com.example.recyclerviewapp.network

import com.example.recyclerviewapp.model.Usuario
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users") // endpoint
    fun getUsuarios(): Call<List<Usuario>>
}
