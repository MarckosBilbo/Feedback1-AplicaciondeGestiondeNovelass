package com.example.feedback1_aplicaciondegestiondenovelass.vista

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.io.File

@Composable
fun MapaUbicacionUsuario(navController: NavHostController) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    AndroidView(factory = { mapView }) {
        // Configuración del mapa
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true) // Habilitar gestos multitáctiles

        // Centrar el mapa en la Península Ibérica inicialmente
        mapView.controller.setCenter(GeoPoint(40.0, -3.0)) // España central
        mapView.controller.setZoom(6.0)

        // Intentar obtener la ubicación del usuario
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val userLocation = GeoPoint(location.latitude, location.longitude)
                    mapView.controller.setCenter(userLocation)

                    val marker = Marker(mapView)
                    marker.position = userLocation
                    marker.title = "Mi Ubicación"
                    mapView.overlays.add(marker)
                } else {
                    // Si no se obtiene la ubicación, centramos en una ubicación específica (Madrid)
                    val defaultLocation = GeoPoint(40.4168, -3.7038) // Madrid, España
                    mapView.controller.setCenter(defaultLocation)
                    val marker = Marker(mapView)
                    marker.position = defaultLocation
                    marker.title = "Ubicación Predeterminada"
                    mapView.overlays.add(marker)
                }
            }
        } else {
            // Si no hay permisos, centramos en una ubicación específica (Madrid)
            val defaultLocation = GeoPoint(40.4168, -3.7038) // Madrid, España
            mapView.controller.setCenter(defaultLocation)
            val marker = Marker(mapView)
            marker.position = defaultLocation
            marker.title = "Ubicación Predeterminada"
            mapView.overlays.add(marker)
        }
    }

    Button(onClick = { navController.popBackStack() }) {
        Text("Volver a la Principal")
    }
}



