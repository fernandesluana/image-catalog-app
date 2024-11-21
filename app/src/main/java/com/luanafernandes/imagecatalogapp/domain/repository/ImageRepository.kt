package com.luanafernandes.imagecatalogapp.domain.repository

import androidx.paging.PagingData
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    fun getFeedImages(): Flow<PagingData<UnsplashImage>>

    suspend fun getImage(imageId: String): UnsplashImage

    fun searchImages(query: String): Flow<PagingData<UnsplashImage>>

    suspend fun toggleFavoriteStatus(image: UnsplashImage)

    fun getFavoriteImages(): Flow<List<String>>

    fun getAllFavoriteImages(): Flow<PagingData<UnsplashImage>>
}