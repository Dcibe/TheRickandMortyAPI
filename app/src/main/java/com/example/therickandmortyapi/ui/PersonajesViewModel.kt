package com.example.therickandmortyapi.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.therickandmortyapi.data.PersonajesRepository
import com.example.therickandmortyapi.model.Personajes_model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class PersonajesViewModel : ViewModel() {

    private val _TAG = "API-DEMO"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("uadedemo")
    private val scope = CoroutineScope(coroutineContext)
    private val personajesRepo = PersonajesRepository()

    var personajes = MutableLiveData<ArrayList<Personajes_model>>()

    // Esta función se encarga de cargar los personajes
    fun cargarPersonajes(context: Context) {
        // Lanzar una nueva corrutina para ejecutar la carga de los personajes en un hilo separado
        scope.launch {
            kotlin.runCatching {
                personajesRepo.getPersonajes(context) // Obtener los personajes desde el repositorio
            }.onSuccess { result ->
                // Cuando la operación tiene éxito
                Log.d(_TAG, "personajes OnSuccess")
                Log.d(_TAG, result.size.toString())
                personajes.postValue(result) // Actualizar los datos de los personajes en el MutableLiveData
            }.onFailure { exception ->
                // Cuando ocurre un error
                Log.e(_TAG, "Personajes Error: $exception")
            }
        }
    }
}