package com.example.feedback1_aplicaciondegestiondenovelass.vista

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.VistaModeloNovela
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.Novel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapaNovelas(navController: NavHostController) {
    val viewModel: VistaModeloNovela = viewModel()
    val novels by viewModel.getAllNovels().observeAsState(emptyList())
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    AndroidView(factory = { mapView }) {
        // Configuración del mapa
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true) // Habilitar gestos multitáctiles

        // Centrar en la Península Ibérica
        mapView.controller.setCenter(GeoPoint(40.0, -3.0)) // Centro: España
        mapView.controller.setZoom(6.0) // Zoom adecuado para abarcar la región

        // Añadir marcadores para las novelas
        novels.forEach { novel: Novel ->
            val marker = Marker(mapView)
            marker.position = GeoPoint(novel.latitude, novel.longitude)
            marker.title = novel.title
            mapView.overlays.add(marker)
        }
    }

    Button(onClick = { navController.popBackStack() }) {
        Text("Volver a la Principal")
    }
}



