package com.mastour.mastour.ui.screen.register

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mastour.mastour.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mastour.mastour.ui.navigation.Screen
import com.mastour.mastour.ui.viewmodel.AuthViewModel
import com.mastour.mastour.util.AuthUiState
import com.mastour.mastour.util.UiState
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val email by viewModel.emailRegister
    val username by viewModel.usernameRegister
    val name by viewModel.nameRegister
    val password by viewModel.passwordRegister
    val confirmPassword by viewModel.passwordConfirm
    val imageUri by viewModel.imageUri

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            viewModel.changeUri(uri)
        }
    }

    val context = LocalContext.current

    viewModel.registerResponse.collectAsState(initial = AuthUiState.Idle).value.let { uiState ->
        when(uiState) {
            is AuthUiState.Idle ->{
                RegisterContent(
                    username = username,
                    name = name,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,
                    imageUri = imageUri,
                    onEmailTextChanged = viewModel::changeEmailRegister,
                    onPasswordTextChanged = viewModel::changePasswordRegister,
                    onNameTextChanged = viewModel::changeNameRegister,
                    onConfirmTextChanged = viewModel::changePasswordConfirm,
                    onUsernameTextChanged = viewModel::changeUsernameRegister,
                    onEditClicked = {
                        launcher.launch("image/*")
                    },
                    onRegisterClicked =viewModel::register,
                    onBackClicked = { /*TODO*/ }
                )
            }
            is AuthUiState.Load -> {
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
            is AuthUiState.Success -> {
                // TODO: Toast or dialogue, Register succeed
                LaunchedEffect(key1 = true){
                    Toast.makeText(context, "Register Successful", Toast.LENGTH_SHORT).show()
                    navHostController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            }
            is AuthUiState.Failure -> {
                // TODO: Toast or dialogue, Register failed
                LaunchedEffect(key1 = true){
                    Toast.makeText(context, "Failed please check if input correct, or check your internet", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

@Composable
fun RegisterContent(
    email: String,
    username: String,
    name: String,
    password: String,
    confirmPassword: String,
    onEmailTextChanged: (String) -> Unit,
    onUsernameTextChanged: (String) -> Unit,
    onNameTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    onConfirmTextChanged: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    imageUri: Uri?,
    onBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.page_background1),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopCenter
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val context = LocalContext.current
        val initialBitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.profile_picture
        )

        Box(modifier = modifier) {
            Image(
                bitmap =
                    if (imageUri != null) {
                        val inputStream = context.contentResolver.openInputStream(imageUri)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        bitmap.asImageBitmap()
                    } else {
                        initialBitmap.asImageBitmap()
                    },
                contentDescription = "Profile Picture",
                contentScale = ContentScale.FillHeight,
                modifier = modifier
                    .size(136.dp)
                    .clip(CircleShape)
            )

            Button(
                onClick = onEditClicked, // TODO: Ask for permission
                contentPadding = PaddingValues(),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .size(40.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colors.primary,
                                    MaterialTheme.colors.secondary
                                )
                            ), shape = CircleShape
                        )
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.White,
                    )
                }
            }
        }

        TextField(
            value = email,
            onValueChange = onEmailTextChanged,
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Password") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                leadingIconColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                placeholderColor = Color.Gray,
                textColor = Color.Black,
            ),
            placeholder = { Text(text = "Email")},
            modifier = Modifier
                .padding(top = 32.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )

        TextField(
            value = username,
            onValueChange = onUsernameTextChanged,
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Username") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                leadingIconColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                placeholderColor = Color.Gray,
                textColor = Color.Black,
            ),
            placeholder = { Text(text = "Username")},
            modifier = Modifier
                .padding(top = 16.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )

        TextField(
            value = name,
            onValueChange = onNameTextChanged,
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Display Name") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                leadingIconColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                placeholderColor = Color.Gray,
                textColor = Color.Black,
            ),
            placeholder = { Text(text = "Display Name")},
            modifier = Modifier
                .padding(top = 16.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )

        TextField(
            value = password,
            onValueChange = onPasswordTextChanged,
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
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

        TextField(
            value = confirmPassword,
            onValueChange = onConfirmTextChanged,
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm Password") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                leadingIconColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                placeholderColor = Color.Gray,
                textColor = Color.Black,
            ),
            placeholder = { Text(text = "Confirm Password")},
            modifier = Modifier
                .padding(top = 16.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )

        Button(
            onClick = onRegisterClicked,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            modifier = Modifier
                .padding(top = 16.dp)
                .width(120.dp)
                .height(48.dp),
            contentPadding = PaddingValues()

        ){
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colors.primary,
                                MaterialTheme.colors.secondary
                            )
                        )
                    )
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ){
                Text(text = "Register", color = Color.White)
            }

        }
    }

//    TODO: Bug on back button, fix later
//    IconButton(onClick = onBackClicked) {
//        Icon(
//            imageVector = Icons.Default.ArrowBack,
//            contentDescription = "Go Back"
//        )
//    }

    Text(text = "Register",
        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(top = 60.dp, start = 30.dp)
    )
}

//@Preview(showBackground = true, device = Devices.PIXEL_4)
//@Composable
//fun RegisterScreenPreview() {
//    MasTourTheme {
//        RegisterContent(username = "", name = "", email = "", password = "", confirmPassword = "",
//            onEmailTextChanged = {}, onPasswordTextChanged = {}, onNameTextChanged = {},
//            onConfirmTextChanged = {}, onUsernameTextChanged = {}, onRegisterClicked = {},
//            onBackClicked = {}, onEditClicked = {})
//    }
//}