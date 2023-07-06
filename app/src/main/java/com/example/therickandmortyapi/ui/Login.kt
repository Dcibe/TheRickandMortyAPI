package com.example.therickandmortyapi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.therickandmortyapi.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Configurar las opciones de inicio de sesión de Google
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Crear el cliente de inicio de sesión de Google
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        // Obtener una instancia de FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Obtener referencia al botón de inicio de sesión
        loginButton = findViewById(R.id.btnLogin)

        // Configurar el evento onClick del botón de inicio de sesión
        loginButton.setOnClickListener {
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 100)
        }
    }

    // Método que se llama cuando se obtiene el resultado de una actividad iniciada con startActivityForResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            } catch (e: Exception) {
                Log.d("DEMO", "onActivityResult: ${e.message}")
            }
        }
    }

    // Método para autenticar al usuario con una cuenta de Google en Firebase
    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        // Obtener las credenciales de la cuenta de Google
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)

        // Iniciar sesión en Firebase con las credenciales obtenidas
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser.email

                if (authResult.additionalUserInfo!!.isNewUser) {
                    // La cuenta de usuario es nueva, realizar acciones correspondientes
                    Toast.makeText(this@Login, "Cuenta creada...", Toast.LENGTH_LONG).show()
                } else {
                    // La cuenta de usuario ya existe
                    Toast.makeText(this@Login, "Cuenta existente...", Toast.LENGTH_LONG).show()
                }

                // Iniciar la actividad principal
                startActivity(Intent(this@Login, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                // El inicio de sesión falló
                Toast.makeText(this@Login, "Login fallido...", Toast.LENGTH_LONG).show()
            }
    }
}
