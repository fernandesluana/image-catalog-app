package com.luanafernandes.imagecatalogapp.domain.repository

interface Downloader {

    fun downloadImage(url: String, fileName: String?)
}