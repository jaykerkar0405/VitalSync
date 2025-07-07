package com.bytewise.vitalsync.presentation.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme

@Composable
fun VitalSyncTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}
