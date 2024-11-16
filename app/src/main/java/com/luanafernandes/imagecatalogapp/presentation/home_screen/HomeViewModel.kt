package com.luanafernandes.imagecatalogapp.presentation.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luanafernandes.imagecatalogapp.data.mapper.toDomainModelList
import com.luanafernandes.imagecatalogapp.data.remote.dto.UnsplashImageDto
import com.luanafernandes.imagecatalogapp.di.AppModule
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    var images : List<UnsplashImage> by  mutableStateOf(emptyList())
        private set

    init {
        getImages()
    }

    private fun getImages() {
        viewModelScope.launch{
            val result = AppModule.retrofitService.getFeedImages()
            images = result.toDomainModelList()
        }
    }

}