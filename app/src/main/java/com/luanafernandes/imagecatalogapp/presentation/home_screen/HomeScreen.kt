package com.luanafernandes.imagecatalogapp.presentation.home_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.luanafernandes.imagecatalogapp.R
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import com.luanafernandes.imagecatalogapp.presentation.component.ImageCard
import com.luanafernandes.imagecatalogapp.presentation.component.ImageCatalogTopAppBar
import com.luanafernandes.imagecatalogapp.presentation.component.ImageVerticalGrid
import com.luanafernandes.imagecatalogapp.presentation.component.ZoomedImageCard
import com.luanafernandes.imagecatalogapp.presentation.util.SnackbarEvent
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onImageClick: (String) -> Unit,
    images: LazyPagingItems<UnsplashImage>,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    snackBarEvent: Flow<SnackbarEvent>,
    onToggleFavoriteStatus: (UnsplashImage) -> Unit,
    favoriteImageIds: List<String>,
    onFavoriteClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    var showImagePreview by remember { mutableStateOf(false) }
    var activeImage by remember { mutableStateOf<UnsplashImage?>(null) }

    LaunchedEffect(key1 = true){
        snackBarEvent.collect { event ->
            snackbarHostState.showSnackbar(
                message = event.message,
                duration = event.duration
            )
        }

    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageCatalogTopAppBar(
                scrollBehavior = scrollBehavior,
                onSearchClick = onSearchClick
            )

            ImageVerticalGrid(
                images = images,
                onImageClick = onImageClick,
                onImageDragStart = {image ->
                    activeImage = image
                    showImagePreview = true
                },
                onImageDragEnd = {
                    showImagePreview = false
                },
                onToggleFavoriteStatus = onToggleFavoriteStatus,
                favoriteImageIds = favoriteImageIds
            )
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            onClick = { onFavoriteClick() }

        ) {
            Icon(
                painter = painterResource(R.drawable.ic_save) ,
                contentDescription = "Favorites",
                tint = MaterialTheme.colorScheme.background
            )
        }
        ZoomedImageCard(
            modifier = Modifier.padding(20.dp),
            isVisible = showImagePreview,
            image = activeImage
        )
    }


}