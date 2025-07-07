package com.bytewise.vitalsync.presentation.utils

import android.os.Build
import android.net.Network
import android.content.Context
import android.net.NetworkRequest
import kotlinx.coroutines.flow.Flow
import android.net.NetworkCapabilities
import android.net.ConnectivityManager
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.distinctUntilChanged
import com.bytewise.vitalsync.presentation.models.NetworkState

class NetworkManager(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private fun isEmulator(): Boolean {
        return (Build.FINGERPRINT.contains("generic") || Build.MODEL.contains("google_sdk") || Build.MODEL.contains(
            "Emulator"
        ) || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains("Genymotion") || Build.BRAND.startsWith(
            "generic"
        ) || Build.DEVICE.startsWith("generic") || Build.PRODUCT.contains("sdk") || Build.PRODUCT.contains(
            "emulator"
        ) || Build.HARDWARE.contains("goldfish") || Build.HARDWARE.contains("ranchu") || Build.HARDWARE.contains(
            "cuttlefish"
        ))
    }

    private fun NetworkCapabilities.hasInternetCapability(): Boolean {
        return if (isEmulator()) {
            true
        } else {
            this.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        }
    }

    fun getNetworkState(): NetworkState {
        val network = connectivityManager.activeNetwork ?: return NetworkState.Disconnected
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return NetworkState.Disconnected

        val hasConnection = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        val hasInternet = capabilities.hasInternetCapability()

        return if (hasConnection) {
            NetworkState.Connected(hasInternet = hasInternet)
        } else {
            NetworkState.Disconnected
        }
    }

    fun observeNetworkState(): Flow<NetworkState> = callbackFlow {
        trySend(NetworkState.Loading)

        val networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                val capabilities = connectivityManager.getNetworkCapabilities(network)

                if (capabilities != null) {
                    val hasConnection =
                        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    val hasInternet = capabilities.hasInternetCapability()

                    if (hasConnection) {
                        trySend(NetworkState.Connected(hasInternet = hasInternet))
                    } else {
                        trySend(NetworkState.Disconnected)
                    }
                } else {
                    trySend(NetworkState.Disconnected)
                }
            }

            override fun onLost(network: Network) {
                trySend(NetworkState.Disconnected)
            }

            override fun onCapabilitiesChanged(
                network: Network, networkCapabilities: NetworkCapabilities
            ) {
                val hasConnection =
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                val hasInternet = networkCapabilities.hasInternetCapability()

                if (hasConnection) {
                    trySend(NetworkState.Connected(hasInternet = hasInternet))
                } else {
                    trySend(NetworkState.Disconnected)
                }
            }
        }

        val networkRequest =
            NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

        trySend(getNetworkState())

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }.distinctUntilChanged()
}
