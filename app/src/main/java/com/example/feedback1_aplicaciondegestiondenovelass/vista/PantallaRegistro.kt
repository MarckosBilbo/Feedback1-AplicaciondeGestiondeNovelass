package com.example.feedback1_aplicaciondegestiondenovelass.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class PantallaRegistro : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this@PantallaRegistro) { task ->
                                if (task.isSuccessful) {
                                    val user = auth.currentUser
                                    user?.sendEmailVerification()?.addOnCompleteListener { verifyTask ->
                                        if (verifyTask.isSuccessful) {
                                            Toast.makeText(this@PantallaRegistro, "Registrado con exito.", Toast.LENGTH_SHORT).show()
                                            startActivity(Intent(this@PantallaRegistro, PantallaLogin::class.java))
                                            finish()
                                        } else {
                                            Toast.makeText(this@PantallaRegistro, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } else {
                                    Toast.makeText(this@PantallaRegistro, "Contrasenia (mayusculas, numeros y letras) necesita / Email generico necesita.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Register")
                }
            }
        }
    }
}