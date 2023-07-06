package com.example.therickandmortyapi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.therickandmortyapi.R
import com.example.therickandmortyapi.model.Personajes_model

class PersonajesList : AppCompatActivity() {
    private lateinit var rvPersonajes: RecyclerView
    private lateinit var adapter: PersonajesAdapter
    private lateinit var viewModel: PersonajesViewModel
    private val progressDialog by lazy { CustomProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_personajes)

        //Llamada a la función para configurar las vistas y el ViewModel
        bindViews()
        bindViewModel()
    }

    override fun onStart() {
        super.onStart()
        progressDialog.start("Recuperando datos...")

        // Llamada al ViewModel para cargar los personajes
        viewModel.cargarPersonajes(this)
    }

    private fun bindViews() {
        // Obtención de la referencia al RecyclerView en el layout
        rvPersonajes = findViewById(R.id.rvPersonajes)

        // Configuración del RecyclerView con un LinearLayoutManager
        rvPersonajes.layoutManager = LinearLayoutManager(this)

        // Creación e configuración del adaptador
        adapter = PersonajesAdapter()
        rvPersonajes.adapter = adapter

        // Configuración del listener para el evento de clic en un elemento del RecyclerView
        adapter.setOnItemClickListener { personaje ->
            mostrarDetallePersonaje(personaje)
        }
    }

    private fun bindViewModel() {
        // Creación del ViewModel utilizando ViewModelProvider
        viewModel = ViewModelProvider(this).get(PersonajesViewModel::class.java)

        // Observación de los cambios en la lista de personajes a través de LiveData
        viewModel.personajes.observe(this, Observer { personajes ->
            // Mapeo de los objetos del ViewModel a una lista de personajes personalizados
            val listaPersonajes = personajes.map { personajeModel ->
                Personajes_model(
                    personajeModel.id,
                    personajeModel.name,
                    personajeModel.status,
                    personajeModel.species,
                    personajeModel.type,
                    personajeModel.gender,
                    personajeModel.image,
                    personajeModel.url,
                    personajeModel.created
                )
            }

            // Actualización de los datos del adaptador con la nueva lista de personajes
            adapter.update(ArrayList(listaPersonajes))

            // Detención del diálogo de progreso
            progressDialog.stop()
        })
    }

    private fun mostrarDetallePersonaje(personaje: Personajes_model) {
        // Creación de un intent para abrir la actividad de detalle del personaje
        val intent = Intent(this, TemCharacterActivity::class.java)

        // Agregando los datos del personaje como extras en el intent
        intent.putExtra("id", personaje.id.toString())
        intent.putExtra("foto", personaje.image)
        intent.putExtra("informacion", personaje.name)
        intent.putExtra("status", personaje.status)
        intent.putExtra("species", personaje.species)
        intent.putExtra("type", personaje.type)
        intent.putExtra("gender", personaje.gender)
        intent.putExtra("created", personaje.created)

        // Iniciando la actividad de detalle del personaje
        startActivity(intent)
    }
}