package com.luanafernandes.imagecatalogapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luanafernandes.imagecatalogapp.data.local.dao.FavoriteImagesDao
import com.luanafernandes.imagecatalogapp.data.local.entity.FavoriteImagesEntity

@Database(
    entities = [FavoriteImagesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ImageCatalogDatabase: RoomDatabase() {
    abstract fun favoriteImagesDao(): FavoriteImagesDao

}