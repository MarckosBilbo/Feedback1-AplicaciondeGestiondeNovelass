package com.example.feedback1_aplicaciondegestiondenovelass.vista

import androidx.compose.ui.tooling.preview.Preview
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.VistaModeloNovela
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.Novel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import com.example.feedback1_aplicaciondegestiondenovelass.ui.theme.Feedback1AplicacióndeGestióndeNovelassTheme

@Preview(showBackground = true)
@Composable
fun PreviewPantallaPrincipal() {
    Feedback1AplicacióndeGestióndeNovelassTheme {
        PantallaPrincipal()
    }
}

@Composable
fun PantallaPrincipal(viewModel: VistaModeloNovela = viewModel()) {
    val novels by viewModel.getAllNovels().observeAsState(emptyList())
    var selectedNovel by remember { mutableStateOf<Novel?>(null) }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var synopsis by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                    val yearValue = year.toIntOrNull() ?: -1
                    if (isEditing && selectedNovel != null) {
                        selectedNovel?.let {
                            it.title = title
                            it.author = author
                            it.year = yearValue
                            it.synopsis = synopsis
                            viewModel.update(it)
                        }
                    } else {
                        viewModel.insert(Novel(title, author, yearValue, synopsis))
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
                }, onDelete = { viewModel.delete(novel) }, onFavorite = { viewModel.toggleFavorite(novel) })
            }
        }

        selectedNovel?.let { novel ->
            NovelDetails(novel)
        }
    }
}

@Composable
fun NovelItem(novel: Novel?, onClick: () -> Unit, onDelete: () -> Unit, onFavorite: () -> Unit) {
    if (novel == null) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = novel.title ?: "Unknown Title",
            style = MaterialTheme.typography.bodyLarge,
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
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Título: ${novel.title}", style = MaterialTheme.typography.titleLarge)
        Text(text = "Autor: ${novel.author}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Año: ${if (novel.year == -1) "Año no especificado" else novel.year.toString()}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Sinopsis: ${novel.synopsis}", style = MaterialTheme.typography.bodyMedium)
    }
}