package com.luanafernandes.imagecatalogapp.data.mapper

import com.luanafernandes.imagecatalogapp.data.local.entity.FavoriteImagesEntity
import com.luanafernandes.imagecatalogapp.data.local.entity.UnsplashImageEntity
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

fun UnsplashImageDto.toEntity(): UnsplashImageEntity {
    return UnsplashImageEntity(
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


fun UnsplashImage.toFavoriteImageEntity(): FavoriteImagesEntity {
    return FavoriteImagesEntity(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImgUrl = this.photographerProfileImgUrl,
        photographerProfileLink = this.photographerProfileLink,
        description = this.description,
        height = this.height,
        width = this.width
    )
}

fun FavoriteImagesEntity.toDomainModel(): UnsplashImage {
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImgUrl = this.photographerProfileImgUrl,
        photographerProfileLink = this.photographerProfileLink,
        description = this.description,
        height = this.height,
        width = this.width
    )
}

fun UnsplashImageEntity.toDomainModel(): UnsplashImage {
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.imageUrlSmall,
        imageUrlRegular = this.imageUrlRegular,
        imageUrlRaw = this.imageUrlRaw,
        photographerName = this.photographerName,
        photographerUsername = this.photographerUsername,
        photographerProfileImgUrl = this.photographerProfileImgUrl,
        photographerProfileLink = this.photographerProfileLink,
        description = this.description,
        height = this.height,
        width = this.width
    )
}

fun List<UnsplashImageDto>.toDomainModelList(): List<UnsplashImage> {
    return this.map { it.toDomainModel() }
}

fun List<UnsplashImageDto>.toEntityList(): List<UnsplashImageEntity> {
    return this.map { it.toEntity() }
}