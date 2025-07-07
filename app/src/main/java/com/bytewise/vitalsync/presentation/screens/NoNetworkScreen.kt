package com.bytewise.vitalsync.presentation.screens

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.wear.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.background
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material.icons.outlined.Refresh

@Composable
fun NoNetworkScreen(
    onRetry: () -> Unit, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.WifiOff,
                contentDescription = "No Internet",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )

            Text(
                text = "No Internet", style = MaterialTheme.typography.title3.copy(
                    fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                ), color = Color.White, textAlign = TextAlign.Center
            )

            Text(
                text = "Check your connection.", style = MaterialTheme.typography.body2.copy(
                    fontSize = 13.sp, fontWeight = FontWeight.Normal
                ), color = Color.White.copy(alpha = 0.7f), textAlign = TextAlign.Center
            )

            Button(
                onClick = onRetry,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .clip(CircleShape)
                    .size(48.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White, contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "Retry",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
