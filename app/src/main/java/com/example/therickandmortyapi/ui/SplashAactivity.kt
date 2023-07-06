package com.example.therickandmortyapi.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.therickandmortyapi.R

class SplashAactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //generar un loop para el Splash, abre la segunda pantalla y se cierra el splash.
        Handler(Looper.getMainLooper()).postDelayed({
            // variable que se supone que recibe el dato del login
            var userName = "abcdefghi"
            // SharedPreferencesse usa como unavariable global para todas las pantallas
            var prefs = getSharedPreferences("com.example.therickandmortyapia", Context.MODE_PRIVATE)
            prefs.edit().putString("user", userName).apply()

            var intent = Intent(this, MainActivity::class.java)
            // putExtra pasa un nombre y un valor de la pantalla a la segunda pantalla.
            intent.putExtra("ID", 1234456)
            startActivity(intent)
            finish()}, 4000)

    }
}