package com.luanafernandes.imagecatalogapp.data.remote

import com.luanafernandes.imagecatalogapp.data.remote.dto.UnsplashImageDto
import com.luanafernandes.imagecatalogapp.data.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers

interface UnsplashApiService {

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/photos")
    suspend fun getFeedImages() : List<UnsplashImageDto>

}