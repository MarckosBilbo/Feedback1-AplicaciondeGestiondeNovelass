package com.example.feedback1_aplicaciondegestiondenovelass

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.feedback1_aplicaciondegestiondenovelass.service.ConnectivityWorker
import com.example.feedback1_aplicaciondegestiondenovelass.vista.PantallaLogin
import com.example.feedback1_aplicaciondegestiondenovelass.vista.PantallaPrincipal
import com.example.feedback1_aplicaciondegestiondenovelass.vista.PantallaConfiguracion
import com.example.feedback1_aplicaciondegestiondenovelass.vista.ListaNovelasFragment
import com.example.feedback1_aplicaciondegestiondenovelass.vista.DetallesNovelaFragment
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.VistaModeloConfiguracion
import com.example.feedback1_aplicaciondegestiondenovelass.ui.theme.Feedback1AplicacióndeGestióndeNovelassTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            startActivity(Intent(this, PantallaLogin::class.java))
            finish()
            return
        }

        setContent {
            val viewModel: VistaModeloConfiguracion = viewModel()
            val isDarkMode by viewModel.isDarkMode.observeAsState(initial = false)

            Feedback1AplicacióndeGestióndeNovelassTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "main") {
                    composable("main") { PantallaPrincipal(navController, isDarkMode) }
                    composable("settings") { PantallaConfiguracion(navController, isDarkMode) }
                    composable("lista_novelas") { ListaNovelasFragment() }
                    composable("detalles_novela") { DetallesNovelaFragment() }
                }
            }
        }

        scheduleConnectivityWorker()
    }

    private fun scheduleConnectivityWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<ConnectivityWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }
}