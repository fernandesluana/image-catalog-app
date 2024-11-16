package com.luanafernandes.imagecatalogapp.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import com.luanafernandes.imagecatalogapp.presentation.home_screen.component.ImageCard
import com.luanafernandes.imagecatalogapp.presentation.home_screen.component.ImageVerticalGrid

@Composable
fun HomeScreen(
    images: List<UnsplashImage>,
    onImageClick: (String) -> Unit
) {
    ImageVerticalGrid(
        images = images,
        onImageClick = onImageClick
    )
}