package com.luanafernandes.imagecatalogapp.data.util

import com.luanafernandes.imagecatalogapp.BuildConfig

object Constants {

    const val IV_LOG_TAG = "ImageCatalogLogs"

    const val API_KEY = BuildConfig.UNSPLASH_API_KEY
    const val BASE_URL = "https://api.unsplash.com/"

    const val ITEMS_PER_PAGE = 10

    const val IMAGE_CATALOG_DATABASE = "image_catalog.db"
    const val FAVORITE_IMAGES_TABLE = "favorite_images_table"

}