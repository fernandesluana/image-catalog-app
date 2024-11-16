package com.luanafernandes.imagecatalogapp.data.mapper

import com.luanafernandes.imagecatalogapp.data.remote.dto.UnsplashImageDto
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage

fun UnsplashImageDto.toDomainModel(): UnsplashImage {
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.urls.small,
        imageUrlRegular = this.urls.regular,
        imageUrlRaw = this.urls.raw,
        photographerName = this.user.name,
        photographerUsername = this.user.username,
        photographerProfileImgUrl = this.user.profile_image.small,
        photographerProfileLink = this.user.links.html,
        description = this.description,
        height = this.height,
        width = this.width

    )
}

fun List<UnsplashImageDto>.toDomainModelList(): List<UnsplashImage> {
    return this.map { it.toDomainModel() }
}