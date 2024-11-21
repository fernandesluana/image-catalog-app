package com.luanafernandes.imagecatalogapp.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.luanafernandes.imagecatalogapp.data.repository.ImageRepositoryImpl
import com.luanafernandes.imagecatalogapp.domain.model.UnsplashImage
import com.luanafernandes.imagecatalogapp.presentation.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ImageRepositoryImpl
): ViewModel() {

    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()

    private val _searchImages = MutableStateFlow<PagingData<UnsplashImage>>(PagingData.empty())
    val searchImages = _searchImages

    fun searchImages(query: String){
        viewModelScope.launch {
            try {
                repository
                    .searchImages(query)
                    .cachedIn(viewModelScope)
                    .collect {
                    _searchImages.value = it
                }
            } catch (e: UnknownHostException){
                _snackbarEvent.send(SnackbarEvent(message = "Something went wrong. ${e.message}"))
            }
        }
    }



}