package com.example.feedback1_aplicaciondegestiondenovelass.vista

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.VistaModeloNovela
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.VistaModeloConfiguracion
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.Novel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PantallaPrincipal(navController: NavController, isDarkMode: Boolean, viewModel: VistaModeloNovela = viewModel(), configViewModel: VistaModeloConfiguracion = viewModel()) {
    val isDarkModeState by configViewModel.isDarkMode.observeAsState(initial = isDarkMode)
    val novels by viewModel.getAllNovels().observeAsState(emptyList())
    var selectedNovel by remember { mutableStateOf<Novel?>(null) }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var synopsis by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val backgroundColor = if (isDarkModeState) Color.DarkGray else Color.White

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Año") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = synopsis,
                onValueChange = { synopsis = it },
                label = { Text("Sinopsis") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (title.isNotEmpty() && author.isNotEmpty() && year.isNotEmpty() && synopsis.isNotEmpty()) {
                        if (isEditing) {
                            selectedNovel?.let {
                                it.title = title
                                it.author = author
                                it.year = year.toIntOrNull() ?: -1
                                it.synopsis = synopsis
                                viewModel.update(it)
                            }
                        } else {
                            viewModel.insert(Novel(title, author, year.toIntOrNull() ?: -1, synopsis))
                        }
                        title = ""
                        author = ""
                        year = ""
                        synopsis = ""
                        isEditing = false
                        selectedNovel = null
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(if (isEditing) "Actualizar Novela" else "Agregar Novela")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(novels) { novel ->
                    NovelItem(novel, onClick = {
                        selectedNovel = novel
                        title = novel.title ?: ""
                        author = novel.author
                        year = if (novel.year == -1) "Año no especificado" else novel.year.toString()
                        synopsis = novel.synopsis
                        isEditing = true
                    }, onDelete = { viewModel.delete(novel) }, onFavorite = { viewModel.toggleFavorite(novel) }, isDarkMode = isDarkModeState)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "Click Novela = (Info + Edit)",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 28.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            selectedNovel?.let { novel ->
                NovelDetails(novel)
            }
        }

        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                context.startActivity(Intent(context, PantallaLogin::class.java))
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Text("Log-out")
        }

        Button(
            onClick = { navController.navigate("settings") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .padding(bottom = 48.dp) // Adjust this value as needed
        ) {
            Text("Configuración")
        }
    }
}

@Composable
fun NovelItem(novel: Novel?, onClick: () -> Unit, onDelete: () -> Unit, onFavorite: () -> Unit, isDarkMode: Boolean) {
    if (novel == null) return

    val textColor = if (isDarkMode) Color.White else Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = novel.title ?: "Unknown Title",
            style = MaterialTheme.typography.bodyLarge.copy(color = textColor),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onDelete) {
            Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
        }
        IconButton(onClick = onFavorite) {
            Icon(if (novel.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, contentDescription = "Favorito")
        }
    }
}



@Composable
fun NovelDetails(novel: Novel) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Título: ${novel.title}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Autor: ${novel.author}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Año: ${if (novel.year == -1) "Año no especificado" else novel.year.toString()}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Sinopsis: ${novel.synopsis}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}