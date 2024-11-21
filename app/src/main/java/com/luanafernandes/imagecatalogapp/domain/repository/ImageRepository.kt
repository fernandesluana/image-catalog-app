package com.luanafernandes.imagecatalogapp.domain.repository

import androidx.paging.PagingData
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    suspend fun getFeedImages(): List<UnsplashImage>

    suspend fun getImage(imageId: String): UnsplashImage

    fun searchImages(query: String): Flow<PagingData<UnsplashImage>>
}