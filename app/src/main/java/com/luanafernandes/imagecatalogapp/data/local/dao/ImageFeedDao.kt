package com.luanafernandes.imagecatalogapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.luanafernandes.imagecatalogapp.data.local.entity.UnsplashImageEntity
import com.luanafernandes.imagecatalogapp.data.local.entity.UnsplashRemoteKeys

@Dao
interface ImageFeedDao {

    @Query("SELECT * FROM unsplash_image_table")
    fun getAllFeedImages(): PagingSource<Int, UnsplashImageEntity>

    @Upsert
    suspend fun insertFeedImages(images: List<UnsplashImageEntity>)

    @Query("DELETE FROM unsplash_image_table")
    suspend fun deleteAllFeedImages()

    @Query("SELECT * FROM unsplash_remote_keys_table WHERE id = :id")
    suspend fun getRemoteKeys(id: String): UnsplashRemoteKeys

    @Upsert
    suspend fun insertAllRemoteKeys(remoteKeys: List<UnsplashRemoteKeys>)

    @Query("DELETE FROM unsplash_remote_keys_table")
    suspend fun deleteAllRemoteKeys()



}