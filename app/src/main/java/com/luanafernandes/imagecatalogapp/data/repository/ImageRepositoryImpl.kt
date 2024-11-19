package com.luanafernandes.imagecatalogapp.data.repository

import com.luanafernandes.imagecatalogapp.data.mapper.toDomainModel
import com.luanafernandes.imagecatalogapp.data.mapper.toDomainModelList
import com.luanafernandes.imagecatalogapp.data.remote.UnsplashApiService
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import com.luanafernandes.imagecatalogapp.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor (
    private val unsplashApi: UnsplashApiService
): ImageRepository {
    override suspend fun getFeedImages(): List<UnsplashImage> {
        return unsplashApi.getFeedImages().toDomainModelList()
    }

    override suspend fun getImage(imageId: String): UnsplashImage {
        return unsplashApi.getImage(imageId).toDomainModel()
    }
}