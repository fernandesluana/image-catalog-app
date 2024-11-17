package com.luanafernandes.imagecatalogapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.luanafernandes.imagecatalogapp.presentation.home_screen.HomeScreen
import com.luanafernandes.imagecatalogapp.presentation.home_screen.HomeViewModel
import com.luanafernandes.imagecatalogapp.presentation.navigation.NavGraphSetup
import com.luanafernandes.imagecatalogapp.presentation.theme.ImageCatalogAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            ImageCatalogAppTheme {
                val navController = rememberNavController()
                val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehaviour.nestedScrollConnection)
                ) {
                    NavGraphSetup(
                        navController = navController,
                        scrollBehaviour = scrollBehaviour
                    )

                }




            }
        }
    }
}

