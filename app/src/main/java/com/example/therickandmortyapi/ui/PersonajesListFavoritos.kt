package com.example.therickandmortyapi.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.therickandmortyapi.R
import com.example.therickandmortyapi.data.FavoritosDataSource
import com.example.therickandmortyapi.model.Personajes_model

class PersonajesListFavoritos : AppCompatActivity() {

    private val TAG = "PersonajesListFavoritos"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PersonajesAdapter
    private val progressDialog by lazy { CustomProgressDialog(this) }

    private val favoritosDataSource = FavoritosDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_personajes_favoritos)

        recyclerView = findViewById(R.id.rvPerFv)
        adapter = PersonajesAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        progressDialog.start("Recuperando datos...")

        // Obtener los personajes guardados en Firebase
        favoritosDataSource.getPersonajesFromFirebase(object : FavoritosDataSource.DataSourceListener {
            override fun onDataLoaded(personajesList: List<Personajes_model>) {
                // Cuando se cargan los datos correctamente, se actualiza el adaptador del RecyclerView
                adapter.update(personajesList)
                progressDialog.stop()
            }

            override fun onDataError(exception: Exception) {
                // Si ocurre un error al obtener los personajes de Firebase, se muestra un mensaje de error en el Logcat
                progressDialog.stop()
                Log.e(TAG, "Error al obtener los personajes de Firebase: $exception")
            }

            override fun onPersonajeExist(existePersonaje: Boolean) {
                TODO("Not yet implemented")
            }
        })
    }
}
