package com.example.therickandmortyapi.data

import android.util.Log
import com.example.therickandmortyapi.model.Personajes_model
import com.google.firebase.firestore.FirebaseFirestore

class FavoritosDataSource {

    private val TAG = "FavoritosDataSource"
    private val firestore = FirebaseFirestore.getInstance()

    // Interfaz que define los métodos para recibir los resultados y manejar los errores
    interface DataSourceListener {
        fun onDataLoaded(personajesList: List<Personajes_model>)
        fun onDataError(exception: Exception)
        fun onPersonajeExist(existePersonaje: Boolean)
    }

    // Método para obtener los personajes favoritos de Firebase
    fun getPersonajesFromFirebase(listener: DataSourceListener) {
        firestore.collection("personajes")
            .get()
            .addOnSuccessListener { result ->
                // Lista para almacenar los personajes obtenidos
                val personajesList = ArrayList<Personajes_model>()

                // Recorrer los documentos obtenidos de Firebase
                for (document in result) {
                    // Obtener los datos del documento y crear un objeto Personajes_model
                    val id = document.id.toInt()
                    val nombre = document.getString("name") ?: ""
                    val species = document.getString("species") ?: ""
                    val img = document.getString("img") ?: ""

                    val personaje = Personajes_model(id, nombre, "", species, "", "", img, "", "")
                    personajesList.add(personaje)
                }

                // Llamar al método onDataLoaded del listener y pasar la lista de personajes
                listener.onDataLoaded(personajesList)
            }
            .addOnFailureListener { exception ->
                // Si ocurre un error, llamar al método onDataError del listener y pasar la excepción
                listener.onDataError(exception)
                Log.e(TAG, "Error al obtener los personajes de Firebase: $exception")
            }
    }

    // Método para escribir datos en Firestore
    fun writeToFirestore(id: String, nombre: String, species: String, img: String) {
        val db = FirebaseFirestore.getInstance()

        val data = hashMapOf(
            "name" to nombre,
            "species" to species,
            "img" to img
        )

        db.collection("personajes")
            .document(id)
            .set(data)
            .addOnSuccessListener {
                // Éxito al escribir los datos en Firestore
            }
            .addOnFailureListener { e ->
                // Error al escribir los datos en Firestore
            }
    }

    // Método para leer datos de Firestore
    fun readFromFirestore(id: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("personajes")
            .document(id)
            .get()
            .addOnSuccessListener { document ->
                val nombre = document.getString("name")
                val species = document.getString("species")
                val img = document.getString("img")

                // Hacer algo con el contenido obtenido
            }
            .addOnFailureListener { e ->
                // Error al leer los datos de Firestore
            }

    }

    // Método para eliminar datos de Firestore
    fun deleteFromFirestore(id: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("personajes")
            .document(id)
            .delete()
            .addOnSuccessListener {
                // Éxito al eliminar el documento de Firestore
            }
            .addOnFailureListener { e ->
                // Error al eliminar el documento de Firestore
            }
    }

    // Método para buscar un personaje en Firebase por su ID
    fun buscarPersonajeEnFirebase(id: String, listener: DataSourceListener) {
        val db = FirebaseFirestore.getInstance()

        db.collection("personajes")
            .document(id)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val existePersonaje = documentSnapshot.exists()

                // Llamar al método onPersonajeExist del listener y pasar el resultado
                listener.onPersonajeExist(existePersonaje)
            }
            .addOnFailureListener { exception ->
                // Si ocurre un error, llamar al método onDataError del listener y pasar la excepción
                listener.onDataError(exception)
                Log.e(TAG, "Error al buscar el personaje en Firebase: $exception")
            }
    }
}

