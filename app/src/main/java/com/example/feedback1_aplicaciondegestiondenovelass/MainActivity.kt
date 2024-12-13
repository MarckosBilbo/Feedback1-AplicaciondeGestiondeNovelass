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
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.feedback1_aplicaciondegestiondenovelass.service.ConnectivityWorker
import com.example.feedback1_aplicaciondegestiondenovelass.vista.PantallaLogin
import com.example.feedback1_aplicaciondegestiondenovelass.vista.PantallaPrincipal
import com.example.feedback1_aplicaciondegestiondenovelass.vista.PantallaConfiguracion
import com.example.feedback1_aplicaciondegestiondenovelass.vista.MapaNovelas
import com.example.feedback1_aplicaciondegestiondenovelass.vista.MapaUbicacionUsuario
import com.example.feedback1_aplicaciondegestiondenovelass.fragments.ListaNovelasFragment
import com.example.feedback1_aplicaciondegestiondenovelass.fragments.DetallesNovelaFragment
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.VistaModeloConfiguracion
import com.example.feedback1_aplicaciondegestiondenovelass.ui.theme.Feedback1AplicacióndeGestióndeNovelassTheme
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.TimeUnit

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

                    // Nuevas pantallas añadidas
                    composable("mapa_novelas") { MapaNovelas(navController) }
                    composable("mapa_usuario") { MapaUbicacionUsuario(navController) }
                }
            }
        }

        scheduleConnectivityWorker()
    }

    // Función para configurar el ConnectivityWorker
    private fun scheduleConnectivityWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<ConnectivityWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "ConnectivityWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}




/*
 Para optimizar la bateria podemos hacer esto:
 1º para el check deberia de verlo en profiler aunque pide batterystats y Battery Historian

// Modificación de la función scheduleConnectivityWorker en MainActivity
private fun scheduleConnectivityWorker() {
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val workRequest = PeriodicWorkRequestBuilder<ConnectivityWorker>(1, TimeUnit.HOURS) // Ajusta el intervalo según sea necesario
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(this).enqueueUniquePeriodicWork(
        "ConnectivityWorker",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}*/






    /*
        RECORDATORIO:


        Por lo general a nivel de memoria ya lo tenemos bastante optimizado pues Usamos:

        Evitar Fugas de Memoria:
        Uso de ExecutorService para manejar tareas en segundo plano, evitando bloqueos en el hilo principal.
        Uso de LiveData para observar cambios en los datos, lo cual ayuda a evitar fugas de memoria.
        Uso Eficiente de LiveData y ViewModel:
        Uso de ViewModel para mantener datos a través de cambios de configuración.
        Uso de LiveData para observar cambios de datos de manera eficiente.
        Reutilización de Objetos:
        Reutilización de objetos en lugar de crear nuevas instancias repetidamente, como se puede ver en la función fetchAllNovels de NovelRepository.
        Reciclaje de Vistas:
        Uso de LazyColumn en PantallaPrincipal.kt para mostrar listas grandes, lo cual solo renderiza los elementos visibles en la pantalla.




        POSIBLES MEJORAS:
        Utiliza WeakReference para evitar fugas de memoria en caso de que mantengas referencias a objetos que pueden ser recolectados por el recolector de basura.
     */

