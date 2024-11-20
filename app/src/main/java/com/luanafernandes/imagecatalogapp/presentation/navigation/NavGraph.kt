package com.luanafernandes.imagecatalogapp.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.luanafernandes.imagecatalogapp.presentation.favorites_screen.FavoritesScreen
import com.luanafernandes.imagecatalogapp.presentation.full_image_screen.FullImageScreen
import com.luanafernandes.imagecatalogapp.presentation.full_image_screen.FullImageViewModel
import com.luanafernandes.imagecatalogapp.presentation.home_screen.HomeScreen
import com.luanafernandes.imagecatalogapp.presentation.home_screen.HomeViewModel
import com.luanafernandes.imagecatalogapp.presentation.profile_screen.ProfileScreen
import com.luanafernandes.imagecatalogapp.presentation.search_screen.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraphSetup(
    navController: NavHostController,
    scrollBehaviour: TopAppBarScrollBehavior
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
                }
            )
        }
        composable<Routes.SearchScreen> {
            SearchScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
        composable<Routes.FavoritesScreen> {
            FavoritesScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
        composable<Routes.FullImageScreen> {
            val fullImageViewModel: FullImageViewModel = hiltViewModel()
            FullImageScreen(
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