package com.example.therickandmortyapi.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.therickandmortyapi.R
import com.example.therickandmortyapi.data.FavoritosDataSource
import com.example.therickandmortyapi.databinding.TemCharacterBinding
import com.example.therickandmortyapi.data.PersonajesDataSource
import com.example.therickandmortyapi.model.Personajes_model

class TemCharacterActivity : AppCompatActivity() {
    private lateinit var binding: TemCharacterBinding
    private var isFavorite: Boolean = false
    private lateinit var personajeId: String
    private lateinit var personajeNombre: String
    private lateinit var personajeSpecie: String
    private lateinit var personajeFoto: String
    private val FavoritosDataSource = FavoritosDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el diseño de la actividad utilizando TemCharacterBinding
        binding = TemCharacterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Obtener los datos del Intent
        val id = intent.getStringExtra("id")
        val foto = intent.getStringExtra("foto")
        val informacion = intent.getStringExtra("informacion")
        val status = intent.getStringExtra("status")
        val species = intent.getStringExtra("species")
        val type = intent.getStringExtra("type")
        val gender = intent.getStringExtra("gender")
        val created = intent.getStringExtra("created")
        personajeId = id ?: ""
        personajeNombre = informacion ?: ""
        personajeSpecie = species ?: ""
        personajeFoto = foto ?: ""

        // Verificar si el personaje es favorito
        if (id != null) {
            // Utilizar el método buscarPersonajeEnFirebase de FavoritosDataSource para comprobar si el personaje es favorito
            FavoritosDataSource.buscarPersonajeEnFirebase(id, object : FavoritosDataSource.DataSourceListener {
                override fun onPersonajeExist(existePersonaje: Boolean) {
                    isFavorite = existePersonaje
                    // Actualizar la imagen del botón de favorito según corresponda
                    if (isFavorite) {
                        binding.btnFavorite.setImageResource(R.drawable.heart_filled)
                    } else {
                        binding.btnFavorite.setImageResource(R.drawable.heart_empty)
                    }
                }

                override fun onDataLoaded(personajesList: List<Personajes_model>) {
                    // No es necesario implementar este método en este caso
                }

                override fun onDataError(exception: Exception) {
                    // Manejar el error si ocurre durante la carga de datos
                }
            })
        }

        // Cargar la imagen utilizando Glide y mostrarla en el ImageView imgLogo
        Glide.with(this)
            .load(foto)
            .placeholder(R.drawable.default_character_image)
            .into(binding.imgLogo)

        // Asignar los valores a los TextView correspondientes
        binding.lblName.text = informacion
        binding.lbspecies.text = species
        binding.lbstatus.text = status
        binding.lbtype.text = type
        binding.lbgender.text = gender
        binding.lbcreated.text = created

        // Configurar el listener del botón "Volver atrás"
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        // Configurar el listener del ImageButton de favorito
        binding.btnFavorite.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                // Guardar el personaje como favorito
                Log.d("PersonajesDataSource", "ID: $personajeId")
                Log.d("PersonajesDataSource", "Nombre: $personajeNombre")
                binding.btnFavorite.setImageResource(R.drawable.heart_filled)
                guardarNombreFirebase(personajeId, personajeNombre, personajeSpecie, personajeFoto)
            } else {
                // Eliminar el personaje de la lista de favoritos
                binding.btnFavorite.setImageResource(R.drawable.heart_empty)
                eliminarNombreFirebase(personajeId)
            }
        }
    }

    // Guardar el personaje en Firebase como favorito
    private fun guardarNombreFirebase(id: String, nombre: String, species: String, img: String) {
        FavoritosDataSource.writeToFirestore(id, nombre, species, img)
    }

    // Eliminar el personaje de la lista de favoritos en Firebase
    private fun eliminarNombreFirebase(id: String) {
        FavoritosDataSource.deleteFromFirestore(id)
    }
}
