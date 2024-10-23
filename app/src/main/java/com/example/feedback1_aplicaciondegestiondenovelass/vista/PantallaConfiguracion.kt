package com.example.feedback1_aplicaciondegestiondenovelass.vista

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.VistaModeloConfiguracion


@Composable
fun PantallaConfiguracion(navController: NavController, isDarkMode: Boolean, viewModel: VistaModeloConfiguracion = viewModel()) {
    val isDarkModeState by viewModel.isDarkMode.observeAsState(initial = isDarkMode)
    val backgroundColor = if (isDarkModeState) Color.DarkGray else Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("FONDO PANTALLA")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.setTheme(!isDarkModeState) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Cambiar fondo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("main") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Volver a Principal")
        }
    }
}