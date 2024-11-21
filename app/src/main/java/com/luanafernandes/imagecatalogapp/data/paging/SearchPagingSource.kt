package com.luanafernandes.imagecatalogapp.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.luanafernandes.imagecatalogapp.data.mapper.toDomainModelList
import com.luanafernandes.imagecatalogapp.data.remote.UnsplashApiService
import com.luanafernandes.imagecatalogapp.data.util.Constants.IV_LOG_TAG
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage

class SearchPagingSource(
    private val unsplashApi: UnsplashApiService,
    private val query: String
): PagingSource<Int, UnsplashImage>() {

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashImage>): Int? {
        Log.d(IV_LOG_TAG, "getRefreshKey: ${state.anchorPosition}")
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashImage> {
        val currentPage = params.key ?: FIRST_PAGE_INDEX
        Log.d(IV_LOG_TAG, "currentPage: $currentPage")
        return try {
            val response = unsplashApi.searchImages(
                query = query,
                page = currentPage,
                perPage = params.loadSize
            )
            val endOfPaginationReached = response.images.isEmpty()
            Log.d(IV_LOG_TAG, "Load result response: ${response.images.toDomainModelList()}")
            Log.d(IV_LOG_TAG, "endOfPaginationReached: $endOfPaginationReached")
            LoadResult.Page(
                data = response.images.toDomainModelList(),
                prevKey = if (currentPage == FIRST_PAGE_INDEX) null else currentPage - 1,
                nextKey = if (endOfPaginationReached) null else currentPage + 1
            )

        } catch (e: Exception){
            Log.d(IV_LOG_TAG, "loadResultError: ${e.message}")
            LoadResult.Error(e)

        }
    }
}