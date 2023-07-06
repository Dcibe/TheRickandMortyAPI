package com.example.therickandmortyapi.ui

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // Aquí puedes agregar cualquier lógica o propiedades que sean específicas de MainViewModel
}












/*package com.example.therickandmortyapi.ui

import android.util.Log
import android.util.MutableDouble
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.therickandmortyapi.data.PersonajesRepository
import com.example.therickandmortyapi.model.Personajes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

// la class siempre hereda de ViewModel
class MainViewModel : ViewModel() {
    //datos que queremos manejar en la vista

    //Constante
    private val _TAG = "API-DEMO"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("uadedemo")
    private val scope = CoroutineScope(coroutineContext)

    //Dependencia
    private val personajesRepo = PersonajesRepository()
    // Propiedades
    var personajes = MutableLiveData<ArrayList<Personajes>>()
        // Funciones
    fun OnStart(){
        //Cargar Datos de los Personajes
        scope.launch {
            kotlin.runCatching {
                personajesRepo.getPersonajes()
            }.onSuccess {
                Log.d(_TAG, "personajes OnSuccess")
                Log.d(_TAG, it.size.toString())
                personajes.postValue(it)
            }.onFailure {
                Log.e(_TAG, "Personajes Error" + it)
            }
        }

    }

}*/