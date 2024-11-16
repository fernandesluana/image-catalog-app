package com.luanafernandes.imagecatalogapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageDto(
    val id: String,
    val description: String?,
    val height: Int,
    val width: Int,
    val urls: Urls,
    val user: UserDto,

    )

@Serializable
data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)

