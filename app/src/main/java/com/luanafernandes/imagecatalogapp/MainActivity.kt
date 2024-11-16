package com.luanafernandes.imagecatalogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.luanafernandes.imagecatalogapp.presentation.home_screen.HomeScreen
import com.luanafernandes.imagecatalogapp.presentation.home_screen.HomeViewModel
import com.luanafernandes.imagecatalogapp.presentation.theme.ImageCatalogAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImageCatalogAppTheme {
                val viewModel = viewModel<HomeViewModel>()

                HomeScreen(images = viewModel.images)



            }
        }
    }
}

