package com.example.feedback1_aplicaciondegestiondenovelass.widgetAdaptado

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.Novel

@Composable
fun WidgetNovelas(favoriteNovels: List<Novel>) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = "Novelas Favoritas (Click)",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )
        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            favoriteNovels.forEach { novel ->
                Text(
                    text = "- ${novel.title ?: "Unknown Title"}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}