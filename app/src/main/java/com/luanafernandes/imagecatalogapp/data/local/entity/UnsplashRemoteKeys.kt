package com.luanafernandes.imagecatalogapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luanafernandes.imagecatalogapp.data.util.Constants.UNSPLASH_REMOTE_KEYS_TABLE

@Entity(tableName = UNSPLASH_REMOTE_KEYS_TABLE)
data class UnsplashRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
