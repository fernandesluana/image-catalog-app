package com.luanafernandes.imagecatalogapp.data.remote

import com.luanafernandes.imagecatalogapp.data.remote.dto.UnsplashImageDto
import com.luanafernandes.imagecatalogapp.data.remote.dto.UnsplashImageSearchResult
import com.luanafernandes.imagecatalogapp.data.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface UnsplashApiService {

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/photos")
    suspend fun getFeedImages() : List<UnsplashImageDto>

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/photos/{id}")
    suspend fun getImage(
        @Path("id") id: String
    ) : UnsplashImageDto

    @Headers("Authorization: Client-ID $API_KEY")
    @GET("/search/photos")
    suspend fun searchImages(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : UnsplashImageSearchResult

}