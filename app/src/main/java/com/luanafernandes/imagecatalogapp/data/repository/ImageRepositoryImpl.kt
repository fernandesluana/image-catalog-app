package com.luanafernandes.imagecatalogapp.data.repository

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.luanafernandes.imagecatalogapp.data.local.ImageCatalogDatabase
import com.luanafernandes.imagecatalogapp.data.mapper.toDomainModel
import com.luanafernandes.imagecatalogapp.data.mapper.toDomainModelList
import com.luanafernandes.imagecatalogapp.data.mapper.toFavoriteImageEntity
import com.luanafernandes.imagecatalogapp.data.paging.SearchPagingSource
import com.luanafernandes.imagecatalogapp.data.remote.UnsplashApiService
import com.luanafernandes.imagecatalogapp.data.util.Constants.ITEMS_PER_PAGE
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import com.luanafernandes.imagecatalogapp.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor (
    private val unsplashApi: UnsplashApiService,
    private val database: ImageCatalogDatabase
): ImageRepository {

    private val favoriteImagesDao = database.favoriteImagesDao()

    override suspend fun getFeedImages(): List<UnsplashImage> {
        return unsplashApi.getFeedImages().toDomainModelList()
    }

    override suspend fun getImage(imageId: String): UnsplashImage {
        return unsplashApi.getImage(imageId).toDomainModel()
    }

    override fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager (
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(unsplashApi, query)
            }
        ).flow
    }

    override suspend fun toggleFavoriteStatus(image: UnsplashImage) {
        val isFavorite = favoriteImagesDao.isImageFavorite(image.id)

        val favoriteImage = image.toFavoriteImageEntity()

        if (isFavorite) {
            favoriteImagesDao.deleteFavoriteImage(favoriteImage)
        } else {
            favoriteImagesDao.insertFavoriteImage(favoriteImage)
        }

    }

    override fun getFavoriteImages(): Flow<List<String>> {
        return favoriteImagesDao.getFavoriteImagesIds()
    }

    override fun getAllFavoriteImages(): Flow<PagingData<UnsplashImage>> {
        return Pager (
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = { favoriteImagesDao.getAllFavoriteImages() }
        )
            .flow
            .map { pagingData ->
                pagingData.map { it.toDomainModel() }
            }
    }
}