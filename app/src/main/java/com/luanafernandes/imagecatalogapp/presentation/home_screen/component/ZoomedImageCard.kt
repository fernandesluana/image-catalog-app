package com.luanafernandes.imagecatalogapp.presentation.home_screen.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import com.skydoves.cloudy.Cloudy

@Composable
fun ZoomedImageCard(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    image: UnsplashImage?
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(image?.imageUrlRegular)
        .crossfade(true)
        .placeholderMemoryCacheKey(MemoryCache.Key(image?.imageUrlSmall ?: ""))
        .build()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isVisible) {
            Cloudy(modifier = Modifier.fillMaxSize(), radius = 25) { }
        }
        AnimatedVisibility(visible = isVisible) {
            Card(
                modifier = modifier
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = imageRequest,
                    contentDescription = null
                )
            }
        }
    }


}