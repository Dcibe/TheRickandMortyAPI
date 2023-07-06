package com.example.therickandmortyapi.ui


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.therickandmortyapi.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    // Método que se llama cuando la actividad se detiene
    override fun onStop() {
        super.onStop()
        Log.d("TAG","onStop")
    }

    // Método que se llama cuando la actividad se pausa
    override fun onPause() {
        super.onPause()
        Log.d("TAG","onPause")
    }

    // Método que se llama cuando la actividad se reanuda
    override fun onResume() {
        super.onResume()
        Log.d("TAG","onResume")
    }

    // Método que se llama cuando la actividad se inicia
    override fun onStart() {
        super.onStart()
        Log.d("TAG","onStart")
    }

    // Método que se llama cuando se crea la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener instancia de FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Comprobar si el usuario está logueado
        checkUser()

        // Recibir valor de la pantalla anterior
        val id = intent.extras!!.getInt("ID")
        Log.d("SegundaPantallaTAG", "id: " + id)

        // Para obtener un valor global, se utiliza SharedPreferences
        val prefs = getSharedPreferences("com.example.therickandmortyapia", Context.MODE_PRIVATE)
        val user = prefs.getString("user", "")
        Log.d("SegundaPantallaTAG", "User: " + user)

        // Botón Personajes
        val botonPersonajes = findViewById<Button>(R.id.botonPersonajes)
        botonPersonajes.setOnClickListener {
            // Iniciar la actividad PersonajesList
            val intent = Intent(this, PersonajesList::class.java)
            startActivity(intent)
        }
        // Botón Favoritos
        val botonFavoritos = findViewById<Button>(R.id.botonFavorito)
        botonFavoritos.setOnClickListener {
            // Iniciar la actividad PersonajesListFavoritos
            val intent = Intent(this, PersonajesListFavoritos::class.java)
            startActivity(intent)
        }
    }

    // Método para comprobar si el usuario está logueado
    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            // Usuario no logueado, redirigir a la actividad de inicio de sesión
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}










/*package com.example.therickandmortyapi.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.therickandmortyapi.R

import com.example.therickandmortyapi.model.Personajes


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    //mostrar elementos de lista en interfaz de usuario
    private lateinit var rvPersonajes: RecyclerView
    private lateinit var adapter: PersonajesAdapter

    override fun onStop() {
        super.onStop()
        Log.d("TAG","onStop")
    }
    override fun onPause() {
        super.onPause()
        Log.d("TAG","onPause")
    }
    override fun onResume() {
        super.onResume()
        Log.d("TAG","onResume")
    }
    override fun onStart() {
        super.onStart()
        viewModel.OnStart()
        Log.d("TAG","onStart")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindView()
        bindViewModel()

        //Recibir valor de la pantalla anterior
        val id = intent.extras!!.getInt("ID")
        Log.d("SegundaPantallaTAG", "id: " + id)


        //para llamar al valor global,utilizo la misma linea de codigo del pref.
        var prefs = getSharedPreferences("com.example.therickandmortyapia", Context.MODE_PRIVATE)
        var user = prefs.getString("user", "")
        Log.d("SegundaPantallaTAG", "User: " + user)
    }

    private fun bindView(){
        rvPersonajes = findViewById(R.id.rvPersonajes)
        rvPersonajes.layoutManager = LinearLayoutManager(this)
        adapter =PersonajesAdapter()
        rvPersonajes.adapter = adapter

    }

    private fun bindViewModel(){
        viewModel = ViewModelProvider(this)[ MainViewModel::class.java]
        viewModel.personajes.observe(this){
            //actualizar la lista de la pantalla
            adapter.Update(it)

        }

    }
}*/
