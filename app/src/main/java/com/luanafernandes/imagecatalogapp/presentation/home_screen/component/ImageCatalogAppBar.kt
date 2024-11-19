package com.luanafernandes.imagecatalogapp.presentation.home_screen.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.luanafernandes.imagecatalogapp.R
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCatalogTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    title: String = "Image Catalog",
    onSearchClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(title.split(" ").first())
                    }
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
                        append(" ${title.split(" ").last()}")
                    }
                },
                fontWeight = FontWeight.ExtraBold
            )
        },
        actions = {
            IconButton(onClick = { onSearchClick() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun FullImageViewTopBar(
    modifier: Modifier = Modifier,
    image : UnsplashImage?,
    onBackClick: () -> Unit,
    onPhotographerImageClick: (String) -> Unit,
    onDownloadImageClick: () -> Unit,
    isVisible : Boolean
){
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = {onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go Back"
                )
            }
            AsyncImage(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
                model = image?.photographerProfileImgUrl,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column (
                modifier = Modifier.clickable {
                    image?.let { onPhotographerImageClick(it.photographerProfileImgUrl) }
                }
            ){
                Text(
                    text = image?.photographerName ?: "",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = image?.photographerName ?: "",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onDownloadImageClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = "Download the image",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

        }
    }

}