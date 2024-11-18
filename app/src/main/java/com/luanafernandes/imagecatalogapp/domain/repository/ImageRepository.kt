package com.luanafernandes.imagecatalogapp.domain.repository

import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage

interface ImageRepository {

    suspend fun getFeedImages(): List<UnsplashImage>
}