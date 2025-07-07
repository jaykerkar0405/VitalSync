package com.bytewise.vitalsync.presentation

import android.os.Bundle
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.runtime.collectAsState
import androidx.wear.compose.material.TimeText
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bytewise.vitalsync.presentation.screens.HomeScreen
import com.bytewise.vitalsync.presentation.models.NetworkState
import com.bytewise.vitalsync.presentation.theme.VitalSyncTheme
import com.bytewise.vitalsync.presentation.utils.NetworkManager
import com.bytewise.vitalsync.presentation.screens.LoadingScreen
import com.bytewise.vitalsync.presentation.screens.NoNetworkScreen
import com.bytewise.vitalsync.presentation.viewmodels.NetworkViewModel
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)

        val networkManager = NetworkManager(this)

        setContent {
            VitalSyncApp(networkManager = networkManager)
        }
    }
}

@Composable
fun VitalSyncApp(networkManager: NetworkManager) {
    val networkViewModel: NetworkViewModel = viewModel { NetworkViewModel(networkManager) }
    val networkState by networkViewModel.networkState.collectAsState()

    VitalSyncTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            TimeText()

            when (networkState) {
                is NetworkState.Loading -> {
                    LoadingScreen()
                }

                is NetworkState.Connected -> {
                    if ((networkState as NetworkState.Connected).hasInternet) {
                        HomeScreen()
                    } else {
                        NoNetworkScreen(
                            onRetry = { networkViewModel.retryConnection() })
                    }
                }

                is NetworkState.Disconnected -> {
                    NoNetworkScreen(
                        onRetry = { networkViewModel.retryConnection() })
                }
            }
        }
    }
}
