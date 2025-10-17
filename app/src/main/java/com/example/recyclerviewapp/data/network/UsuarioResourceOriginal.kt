package com.example.recyclerviewapp.data.network

data class UsuarioResourceOriginal(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: AddressResource,
    val phone: String,
    val website: String,
    val company: CompanyResource
)

data class AddressResource(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: GeoResource
)

data class GeoResource(
    val lat: String,
    val lng: String
)

data class CompanyResource(
    val name: String
)
