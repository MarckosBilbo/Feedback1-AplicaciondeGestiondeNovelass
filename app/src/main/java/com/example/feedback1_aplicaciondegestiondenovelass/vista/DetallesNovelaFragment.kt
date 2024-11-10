package com.example.feedback1_aplicaciondegestiondenovelass.vista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.feedback1_aplicaciondegestiondenovelass.modelo.Novel

class DetallesNovelaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val novel = arguments?.getParcelable<Novel>("novel")
        return ComposeView(requireContext()).apply {
            setContent {
                novel?.let { NovelDetails(it) }
            }
        }
    }
}