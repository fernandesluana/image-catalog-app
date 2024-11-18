package com.luanafernandes.imagecatalogapp.data.repository

import com.luanafernandes.imagecatalogapp.data.mapper.toDomainModelList
import com.luanafernandes.imagecatalogapp.data.remote.UnsplashApiService
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import com.luanafernandes.imagecatalogapp.domain.repository.ImageRepository

class ImageRepositoryImpl(
    private val unsplashApi: UnsplashApiService
): ImageRepository {
    override suspend fun getFeedImages(): List<UnsplashImage> {
        return unsplashApi.getFeedImages().toDomainModelList()
    }
}