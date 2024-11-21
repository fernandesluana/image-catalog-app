package com.luanafernandes.imagecatalogapp.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.luanafernandes.imagecatalogapp.presentation.favorites_screen.FavoritesScreen
import com.luanafernandes.imagecatalogapp.presentation.favorites_screen.FavoritesViewModel
import com.luanafernandes.imagecatalogapp.presentation.full_image_screen.FullImageScreen
import com.luanafernandes.imagecatalogapp.presentation.full_image_screen.FullImageViewModel
import com.luanafernandes.imagecatalogapp.presentation.home_screen.HomeScreen
import com.luanafernandes.imagecatalogapp.presentation.home_screen.HomeViewModel
import com.luanafernandes.imagecatalogapp.presentation.profile_screen.ProfileScreen
import com.luanafernandes.imagecatalogapp.presentation.search_screen.SearchScreen
import com.luanafernandes.imagecatalogapp.presentation.search_screen.SearchViewModel
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraphSetup(
    navController: NavHostController,
    scrollBehaviour: TopAppBarScrollBehavior,
    snackbarHostState: SnackbarHostState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
){
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen
    ) {
        composable<Routes.HomeScreen> {
            val homeViewModel : HomeViewModel = hiltViewModel()
            HomeScreen(
                scrollBehavior = scrollBehaviour,
                images = homeViewModel.images,
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onSearchClick = {
                    navController.navigate(Routes.SearchScreen)
                },
                onFavoriteClick = {
                    navController.navigate(Routes.FavoritesScreen)
                },
                snackbarHostState = snackbarHostState,
                snackBarEvent = homeViewModel.snackbarEvent,
            )
        }
        composable<Routes.SearchScreen> {
            val searchViewModel : SearchViewModel = hiltViewModel()
            val searchedImages = searchViewModel.searchImages.collectAsLazyPagingItems()
            val favoriteImagesIds by searchViewModel.favoriteImagesIds.collectAsStateWithLifecycle()
            SearchScreen(
                snackbarHostState = snackbarHostState,
                snackBarEvent = searchViewModel.snackbarEvent,
                searchedImages = searchedImages,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onBackClick = { navController.navigateUp() },
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onSearch = {searchViewModel.searchImages(it)},
                onToggleFavoriteStatus = { searchViewModel.toggleFavoriteStatus(it) },
                favoriteImageIds = favoriteImagesIds
            )
        }
        composable<Routes.FavoritesScreen> {
            val favoritesViewModel : FavoritesViewModel = hiltViewModel()
            val favoriteImages = favoritesViewModel.favoriteImages.collectAsLazyPagingItems()
            val favoriteImagesIds by favoritesViewModel.favoriteImagesIds.collectAsStateWithLifecycle()
            FavoritesScreen(
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                favoriteImages = favoriteImages,
                scrollBehavior = scrollBehaviour,
                snackbarHostState = snackbarHostState,
                snackBarEvent = favoritesViewModel.snackbarEvent,
                onToggleFavoriteStatus = { favoritesViewModel.toggleFavoriteStatus(it) },
                favoriteImageIds = favoriteImagesIds,
                onSearchClick = {navController.navigate(Routes.SearchScreen)},
                onBackClick = { navController.navigateUp() },
            )
        }
        composable<Routes.FullImageScreen> {
            val fullImageViewModel: FullImageViewModel = hiltViewModel()
            FullImageScreen(
                snackbarHostState = snackbarHostState,
                snackBarEvent = fullImageViewModel.snackbarEvent,
                image = fullImageViewModel.image,
                onBackClick = { navController.navigateUp() },
                onPhotographerNameClick = { profileLink ->
                    navController.navigate(Routes.ProfileScreen(profileLink))
                },
                onDownloadImageClick = { url, title ->
                    fullImageViewModel.downloadImage(url, title)
                }
            )
        }
        composable<Routes.ProfileScreen> {backStackEntry ->
            val profileLink = backStackEntry.toRoute<Routes.ProfileScreen>().profileLink
            ProfileScreen(
                profileLink = profileLink,
                onBackClick = { navController.navigateUp() }
            )
        }

    }
}