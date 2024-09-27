package com.example.feedback1_aplicaciondegestiondenovelass


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feedback1_aplicaciondegestiondenovelass.vista.PantallaPrincipal
import com.example.feedback1_aplicaciondegestiondenovelass.ui.theme.Feedback1AplicacióndeGestióndeNovelassTheme
import com.example.feedback1_aplicaciondegestiondenovelass.vista.PantallaPrincipal
import com.example.feedback1_aplicaciondegestiondenovelass.ui.theme.Feedback1AplicacióndeGestióndeNovelassTheme
import com.example.feedback1_aplicaciondegestiondenovelass.ui.theme.Feedback1AplicacióndeGestióndeNovelassTheme
import com.example.feedback1_aplicaciondegestiondenovelass.vista.PantallaPrincipal


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Feedback1AplicacióndeGestióndeNovelassTheme  {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "main") {
                    composable("main") { PantallaPrincipal() }
                }
            }
        }
    }
}