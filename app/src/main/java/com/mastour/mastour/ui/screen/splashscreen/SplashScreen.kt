package com.mastour.mastour.ui.screen.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mastour.mastour.R
import com.mastour.mastour.ui.navigation.Screen
import com.mastour.mastour.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    navHostController: NavHostController
){
    val userExist by viewModel.userExist
    LaunchedEffect(key1 = true){
        viewModel.tryUserExist()
        delay(300L)
        if(userExist){
            navHostController.navigate(Screen.Home.route) {
                popUpTo(0)
            }
        }
        else{
            navHostController.navigate(Screen.Login.route) {
                popUpTo(0)
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.background(
        brush = Brush.horizontalGradient(
            colors = listOf(
                MaterialTheme.colors.primary,
                MaterialTheme.colors.secondary
            )
        )).fillMaxSize(),){
        Image(
            painter = painterResource(id = R.drawable.wayang_only),
            contentDescription = "Matchmaking",
            modifier = Modifier.size(400.dp)
        )
    }
}