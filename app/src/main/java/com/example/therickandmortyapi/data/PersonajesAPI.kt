package com.example.therickandmortyapi.data

import com.example.therickandmortyapi.model.Personajes_model
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

// Interfaz que define las operaciones de la API para obtener personajes
interface PersonajesAPI {
    // Anotación @GET indica que esta función realiza una solicitud HTTP GET a la ruta "/api/character"
    // y espera recibir una respuesta de tipo PersonajesResponse
    @GET("/api/character")
    fun getPersonajes(): Call<PersonajesResponse>

    // Anotación @GET indica que esta función realiza una solicitud HTTP GET a una ruta específica "/api/character/{id}"
    // donde {id} es el parámetro que se reemplazará con el ID del personaje deseado
    // y espera recibir una respuesta de tipo Personajes_model
    @GET("/api/character/{id}")
    fun getPersonajeById(@Path("id") id: String): Call<Personajes_model>
}

// Clase de datos que representa la respuesta de la API de personajes
data class PersonajesResponse(
    val results: ArrayList<Personajes_model>
)
