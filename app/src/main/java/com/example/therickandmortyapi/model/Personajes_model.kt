package com.example.therickandmortyapi.model
//medelo del objeto Personaje
data class Personajes_model(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String?,
    val gender: String,
    val image: String,
    val url: String,
    val created: String
)


