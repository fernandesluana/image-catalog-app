package com.luanafernandes.imagecatalogapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.luanafernandes.imagecatalogapp.data.local.entity.FavoriteImagesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteImagesDao {

    @Query("SELECT * FROM favorite_images_table")
    fun getAllFavoriteImages(): PagingSource<Int, FavoriteImagesEntity>

    @Upsert
    suspend fun insertFavoriteImage(image: FavoriteImagesEntity)

    @Delete
    suspend fun deleteFavoriteImage(image: FavoriteImagesEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_images_table WHERE id = :imageId)")
    suspend fun isImageFavorite(imageId: String): Boolean

    @Query("SELECT id FROM favorite_images_table")
    fun getFavoriteImagesIds(): Flow<List<String>>

}