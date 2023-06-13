package com.mastour.mastour.ui.screen.login

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mastour.mastour.R
import com.mastour.mastour.ui.navigation.Screen
import com.mastour.mastour.ui.theme.MasTourTheme
import com.mastour.mastour.ui.viewmodel.AuthViewModel
import com.mastour.mastour.util.AuthUiState
import com.mastour.mastour.util.isEmailValid

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navHostController: NavHostController,
    modifier: Modifier = Modifier
){
    val email by viewModel.email
    val password by viewModel.password
    val userExist by viewModel.userExist
    val context = LocalContext.current
    val dataValid = remember {
        mutableListOf(false, false)
    }

    SideEffect {
        viewModel.tryUserExist()
    }
    LaunchedEffect(userExist) {
        if (userExist) {
            navHostController.navigate(Screen.Home.route) {
                popUpTo(0)
            }

        }
    }
    viewModel.loginResponse.collectAsState(initial = AuthUiState.Idle).value.let { uiState ->
        when(uiState){
            is AuthUiState.Idle ->{
                LoginContent(
                    email = email,
                    password = password,
                    onEmailTextChanged = viewModel::changeEmail,
                    onPasswordTextChanged = viewModel::changePassword,
                    onLoginClicked = viewModel::login,
                    checkValid = dataValid,
                    onRegisterClicked = {
                        navHostController.navigate(Screen.Register.route){
                            popUpTo(navHostController.graph.findStartDestination().id){
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        } })
            }
            is AuthUiState.Load ->{
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Loading")
                    CircularProgressIndicator(color = Color.Black)
                }
            }
            is AuthUiState.Success ->{
                LoginContent(
                    email = email,
                    password = password,
                    onEmailTextChanged = viewModel::changeEmail,
                    onPasswordTextChanged = viewModel::changePassword,
                    onLoginClicked = viewModel::login,
                    checkValid = dataValid,
                    onRegisterClicked = {
                        navHostController.navigate(Screen.Register.route){
                            popUpTo(navHostController.graph.findStartDestination().id){
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        } })
                LaunchedEffect(key1 = true){
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                }
            }
            is AuthUiState.Failure ->{
                LoginContent(
                    email = email,
                    password = password,
                    onEmailTextChanged = viewModel::changeEmail,
                    onPasswordTextChanged = viewModel::changePassword,
                    onLoginClicked = viewModel::login,
                    checkValid = dataValid,
                    onRegisterClicked = {
                        navHostController.navigate(Screen.Register.route){
                            popUpTo(navHostController.graph.findStartDestination().id){
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    })
                LaunchedEffect(key1 = true){
                    Toast.makeText(context, "Failed please check if input correct, or check your internet", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}

@Composable
fun LoginContent(
    email: String,
    password: String,
    checkValid: MutableList<Boolean>,
    onEmailTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
    modifier: Modifier = Modifier,
){
    checkValid[0] = email.isNotBlank() && isEmailValid(email)
    checkValid[1] = password.isNotBlank()
    val allValid = checkValid.all { it }

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.wayang_background),
                contentScale = ContentScale.Crop,
                alpha = 0.15F
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.wayang_logo),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.size(300.dp)
        )
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Bring Tourism to The Masses",
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Normal, color = Color.Gray),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp),
        )
        TextField(
            value = email,
            onValueChange = onEmailTextChanged,
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email")},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                leadingIconColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                placeholderColor = Color.Gray,
                textColor = Color.Black,
            ),
            placeholder = { Text(text = "E-mail")},
            modifier = Modifier
                .padding(top = 32.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )

        TextField(
            value = password,
            onValueChange = onPasswordTextChanged,
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            singleLine = true,
            visualTransformation =  PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password")},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                leadingIconColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                placeholderColor = Color.Gray,
                textColor = Color.Black,
            ),
            placeholder = { Text(text = "Password")},
            modifier = Modifier
                .padding(top = 16.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )

        Button(
            onClick = onLoginClicked,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            modifier = Modifier
                .padding(top = 16.dp)
                .width(120.dp)
                .height(48.dp),
            contentPadding = PaddingValues()

        ){
            Box(
                modifier = if (allValid) {
                    Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colors.primary,
                                        MaterialTheme.colors.secondary
                                    )
                                )
                            )
                            .fillMaxSize()
                    } else {
                        Modifier
                            .background(Color.Gray)
                            .fillMaxSize()
                    },
                    contentAlignment = Alignment.Center,
            ){
                Text(text = "Login", color = Color.White)
            }
        }
        
        TextButton(onClick = onRegisterClicked) {
            Text(
                text = "Don't have an account? Register here",
                style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Normal, color = MaterialTheme.colors.primary),
            )
        }
    }
}