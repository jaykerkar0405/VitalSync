package com.bytewise.vitalsync.presentation.screens

import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.wear.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp), strokeWidth = 2.dp
            )

            Text(
                text = "Checking connection...", style = MaterialTheme.typography.body2.copy(
                    fontSize = 14.sp, fontWeight = FontWeight.Normal
                ), color = Color.White, textAlign = TextAlign.Center
            )
        }
    }
}
