package com.luanafernandes.imagecatalogapp.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import com.luanafernandes.imagecatalogapp.data.local.ImageCatalogDatabase
import com.luanafernandes.imagecatalogapp.data.local.entity.UnsplashImageEntity
import com.luanafernandes.imagecatalogapp.data.local.entity.UnsplashRemoteKeys
import com.luanafernandes.imagecatalogapp.data.mapper.toEntityList
import com.luanafernandes.imagecatalogapp.data.remote.UnsplashApiService
import com.luanafernandes.imagecatalogapp.data.util.Constants
import com.luanafernandes.imagecatalogapp.data.util.Constants.ITEMS_PER_PAGE

@OptIn(ExperimentalPagingApi::class)
class ImageFeedRemoteMediator(
    private val apiService: UnsplashApiService,
    private val database: ImageCatalogDatabase
): RemoteMediator<Int, UnsplashImageEntity>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    private val imageFeedDao = database.imageFeedDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImageEntity>
    ): MediatorResult {
        try {
            val currentPage = when(loadType) {
                REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: STARTING_PAGE_INDEX
                }

                PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    Log.d(Constants.IV_LOG_TAG, "remoteKeysPrev: ${remoteKeys?.prevPage}")
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success( endOfPaginationReached = remoteKeys != null)
                    prevPage
                }

                APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    Log.d(Constants.IV_LOG_TAG, "remoteKeysNext: ${remoteKeys?.nextPage}")
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success( endOfPaginationReached = remoteKeys != null)
                    nextPage
                }
            }

            val response = apiService.getFeedImages(page = currentPage, perPage = ITEMS_PER_PAGE)
            val endOfPaginationReached = response.isEmpty()
            Log.d(Constants.IV_LOG_TAG, "endOfPaginationReached: $endOfPaginationReached")

            val prevPage = if(currentPage == STARTING_PAGE_INDEX) null else currentPage - 1
            val nextPage = if(endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == REFRESH) {
                    imageFeedDao.deleteAllFeedImages()
                    imageFeedDao.deleteAllRemoteKeys()
                }
                val remoteKeys = response.map { unsplashImageDto ->
                    UnsplashRemoteKeys(
                        id = unsplashImageDto.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                imageFeedDao.insertAllRemoteKeys(remoteKeys)
                imageFeedDao.insertFeedImages(response.toEntityList())
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, UnsplashImageEntity>
    ): UnsplashRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                imageFeedDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, UnsplashImageEntity>
    ): UnsplashRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                imageFeedDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, UnsplashImageEntity>
    ): UnsplashRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                imageFeedDao.getRemoteKeys(id = unsplashImage.id)
            }
    }
}