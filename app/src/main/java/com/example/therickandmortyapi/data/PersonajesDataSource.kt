package com.example.therickandmortyapi.data

import android.content.Context
import android.util.Log
import com.example.therickandmortyapi.data.local.AppDataBase
import com.example.therickandmortyapi.data.local.toExternal
import com.example.therickandmortyapi.data.local.toLocal
import com.example.therickandmortyapi.model.Personajes_model
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PersonajesDataSource {
    // Constantes
    private val _BASE_URL = "https://rickandmortyapi.com/"
    private val _TAG = "API-DEMO"

    // Método para obtener personajes
    suspend fun getPersonajes(context: Context): ArrayList<Personajes_model> {
        Log.d(_TAG, "Personajes Data Source Get")

        // Buscar personajes en la base de datos local
        Log.d(_TAG, "Busco lista Local")
        var pLocal = AppDataBase.getInstance(context).PersonajesDao().getAll()

        // Verificar si se encontraron personajes locales
        if (pLocal.size > 0) {
            delay(3000)
            Log.d(_TAG, "Devuelvo lista Local")
            return pLocal.toExternal() as ArrayList<Personajes_model>
        }

        // Si no hay personajes locales, realizar la solicitud a la API
        // Crear instancia de la API Retrofit
        var api = Retrofit.Builder()
            .baseUrl(_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PersonajesAPI::class.java)

        // Simular una demora en la respuesta de la API (3 segundos)
        delay(3000)

        // Realizar la solicitud a la API para obtener los personajes
        var result = api.getPersonajes().execute()

        // Verificar si la solicitud fue exitosa
        return if (result.isSuccessful) {

            Log.d(_TAG, "Resultado exitoso")
            var plist = result.body()?.results ?: ArrayList<Personajes_model>()

            // Verificar si se obtuvieron personajes de la API
            if (plist.size > 0) {
                Log.d(_TAG, "Resultado Exitos1")

                // Convertir los personajes obtenidos al formato de la base de datos local
                var plistlocal = plist.toLocal().toTypedArray()

                // Insertar los personajes en la base de datos local
                AppDataBase.getInstance(context).PersonajesDao().insert(*plistlocal)
            }

            // Devolver la lista de personajes obtenidos
            plist
        } else {
            // En caso de error en la llamada a la API, registrar el error y devolver una lista vacía
            Log.e(_TAG, "Error de llamada API: " + result.message())
            ArrayList<Personajes_model>()
        }
    }

}
