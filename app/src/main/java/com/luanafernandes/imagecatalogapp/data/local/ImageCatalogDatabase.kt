package com.luanafernandes.imagecatalogapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luanafernandes.imagecatalogapp.data.local.dao.FavoriteImagesDao
import com.luanafernandes.imagecatalogapp.data.local.dao.ImageFeedDao
import com.luanafernandes.imagecatalogapp.data.local.entity.FavoriteImagesEntity
import com.luanafernandes.imagecatalogapp.data.local.entity.UnsplashImageEntity
import com.luanafernandes.imagecatalogapp.data.local.entity.UnsplashRemoteKeys

@Database(
    entities = [FavoriteImagesEntity::class, UnsplashImageEntity::class, UnsplashRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class ImageCatalogDatabase: RoomDatabase() {
    abstract fun favoriteImagesDao(): FavoriteImagesDao

    abstract fun imageFeedDao(): ImageFeedDao


}