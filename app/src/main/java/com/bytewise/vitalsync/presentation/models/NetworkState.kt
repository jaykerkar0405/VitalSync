package com.bytewise.vitalsync.presentation.models

sealed class NetworkState {
    data object Loading : NetworkState()
    data object Disconnected : NetworkState()
    data class Connected(val hasInternet: Boolean = true) : NetworkState()
}
