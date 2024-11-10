package com.example.feedback1_aplicaciondegestiondenovelass.vista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.VistaModeloNovela

class ListaNovelasFragment : Fragment() {

    private lateinit var viewModel: VistaModeloNovela

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(VistaModeloNovela::class.java)
        return ComposeView(requireContext()).apply {
            setContent {
                val navController = rememberNavController()
                PantallaPrincipal(navController = navController, isDarkMode = false, viewModel = viewModel)
            }
        }
    }
}