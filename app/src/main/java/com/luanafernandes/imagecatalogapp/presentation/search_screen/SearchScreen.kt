package com.luanafernandes.imagecatalogapp.presentation.search_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.luanafernandes.imagecatalogapp.R
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import com.luanafernandes.imagecatalogapp.presentation.component.ImageCatalogTopAppBar
import com.luanafernandes.imagecatalogapp.presentation.component.ImageVerticalGrid
import com.luanafernandes.imagecatalogapp.presentation.component.ZoomedImageCard
import com.luanafernandes.imagecatalogapp.presentation.navigation.Routes
import com.luanafernandes.imagecatalogapp.presentation.util.SnackbarEvent
import com.luanafernandes.imagecatalogapp.presentation.util.searchKeywords
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onImageClick: (String) -> Unit,
    onBackClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    snackBarEvent: Flow<SnackbarEvent>
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var showImagePreview by remember { mutableStateOf(false) }

    var isSuggestionsVisible by remember { mutableStateOf(false) }

    var activeImage by remember { mutableStateOf<UnsplashImage?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true){
        snackBarEvent.collect { event ->
            snackbarHostState.showSnackbar(
                message = event.message,
                duration = event.duration
            )
        }
    }

    LaunchedEffect (key1 = Unit){
        delay(500)
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center

    ){
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { isSuggestionsVisible = it.isFocused },
                    query = searchQuery,
                    onQueryChange = { searchQuery = it},
                    onSearch = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
                    placeholder = { Text(text = "Search...") },
                    leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search")
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { if (searchQuery.isNotEmpty()) searchQuery = "" else onBackClick() }
                        ){
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close"
                            )
                        }
                    },
                    active = false,
                    onActiveChange = { },
                    content = {}
                )
                AnimatedVisibility(visible = isSuggestionsVisible) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(searchKeywords){ keyword ->
                            SuggestionChip(
                                onClick = {
                                    searchQuery = keyword
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                },
                                label = {
                                    Text(text = keyword)
                                },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }

                }


                ImageVerticalGrid(
                    images = emptyList(),
                    onImageClick = onImageClick,
                    onImageDragStart = {image ->
                        activeImage = image
                        showImagePreview = true
                    },
                    onImageDragEnd = {
                        showImagePreview = false
                    }
                )
            }

            ZoomedImageCard(
                modifier = Modifier.padding(20.dp),
                isVisible = showImagePreview,
                image = activeImage
            )
        }
    }
}