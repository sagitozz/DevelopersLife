package com.application.developerslife.model.network

/**
 * @autor d.snytko
 */
sealed class NetworkState {
    data class ImageLoaded(val gifResponse: GifItem) : NetworkState()
    data class Error(val error: Exception) : NetworkState()
    object Loading : NetworkState()
}
