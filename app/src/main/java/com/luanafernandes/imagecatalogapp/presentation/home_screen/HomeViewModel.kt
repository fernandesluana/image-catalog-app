package com.luanafernandes.imagecatalogapp.presentation.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luanafernandes.imagecatalogapp.data.repository.ImageRepositoryImpl
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import com.luanafernandes.imagecatalogapp.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ImageRepositoryImpl
): ViewModel() {

    var images : List<UnsplashImage> by  mutableStateOf(emptyList())
        private set

    init {
        getImages()
    }

    private fun getImages() {
        viewModelScope.launch{
            val result = repository.getFeedImages()
            images = result
        }
    }

}