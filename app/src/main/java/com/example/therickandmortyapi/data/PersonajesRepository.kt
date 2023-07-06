package com.example.therickandmortyapi.data


import android.content.Context
import com.example.therickandmortyapi.model.Personajes_model

class PersonajesRepository {
    // Dependencia
    private val personajesDS = PersonajesDataSource()

    // Método para obtener los personajes
    suspend fun getPersonajes(context: Context): ArrayList<Personajes_model> {
        // Llama al método getPersonajes del objeto PersonajesDataSource y devuelve el resultado

        // Creación de una instancia del objeto PersonajesDataSource que se encarga de obtener los personajes.
        // Esta instancia se almacena en la variable "personajesDS" como una dependencia del repositorio.

        return personajesDS.getPersonajes(context)
        // Llama al método getPersonajes del objeto PersonajesDataSource, pasando el contexto como parámetro.
        // Este método es suspendido, lo que significa que puede contener operaciones asincrónicas.
        // La ejecución se detiene hasta que se obtiene el resultado de los personajes.

        // El resultado obtenido del método getPersonajes del PersonajesDataSource se devuelve directamente
        // como el resultado del método getPersonajes del repositorio.
    }
}
