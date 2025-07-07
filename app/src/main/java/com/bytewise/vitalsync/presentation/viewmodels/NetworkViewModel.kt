package com.bytewise.vitalsync.presentation.viewmodels

import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import com.bytewise.vitalsync.presentation.models.NetworkState
import com.bytewise.vitalsync.presentation.utils.NetworkManager

class NetworkViewModel(private val networkManager: NetworkManager) : ViewModel() {
    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.Loading)
    val networkState: StateFlow<NetworkState> = _networkState.asStateFlow()

    init {
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            networkManager.observeNetworkState().collect { state ->
                _networkState.value = state
            }
        }
    }

    fun retryConnection() {
        _networkState.value = NetworkState.Loading
        viewModelScope.launch {
            _networkState.value = networkManager.getNetworkState()
        }
    }
}