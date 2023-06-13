package com.mastour.mastour.ui.screen.failureScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun FailureScreen(
    modifier: Modifier = Modifier,
    onRefreshClicked: () -> Unit
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Load Failed Check Your Internet Connection!")
        TextButton(onClick = onRefreshClicked) {
            Text(
                text = "Refresh",
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Normal, color = MaterialTheme.colors.primary),
            )
        }
    }
}