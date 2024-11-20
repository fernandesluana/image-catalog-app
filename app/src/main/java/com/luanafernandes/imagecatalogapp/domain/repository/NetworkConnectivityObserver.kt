package com.luanafernandes.imagecatalogapp.domain.repository

import com.luanafernandes.imagecatalogapp.domain.model.NetworkStatus
import kotlinx.coroutines.flow.StateFlow

interface NetworkConnectivityObserver {

    val networkStatus : StateFlow<NetworkStatus>
}