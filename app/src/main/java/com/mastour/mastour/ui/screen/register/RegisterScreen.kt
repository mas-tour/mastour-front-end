package com.mastour.mastour.ui.screen.register

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mastour.mastour.R
import com.mastour.mastour.ui.navigation.Screen
import com.mastour.mastour.ui.screen.dialog.GenderSelectionDialog
import com.mastour.mastour.ui.viewmodel.AuthViewModel
import com.mastour.mastour.util.AuthUiState
import com.mastour.mastour.util.isEmailValid
import java.util.*

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    navHostController: NavHostController,

    ) {
    val email by viewModel.emailRegister
    val username by viewModel.usernameRegister
    val name by viewModel.nameRegister
    val password by viewModel.passwordRegister
    val confirmPassword by viewModel.passwordConfirm
    val imageUri by viewModel.imageUri

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.changeUri(uri)
        }
    }

    val context = LocalContext.current

    val dataValid = remember {
        mutableListOf(false, false, false, false, false)
    }
    val genderDialog = remember { mutableStateOf(false) }
    val genders = listOf("male", "female")
    val selectedItem = remember {
        mutableStateOf("")
    }
    GenderSelectionDialog(
        openDialog = genderDialog,
        selectedItem = selectedItem,
        genderOptions = genders,
        onSubmitClicked = {
            viewModel.changeGender(selectedItem.value)
            genderDialog.value = false
        }
    )

    viewModel.registerResponse.collectAsState(initial = AuthUiState.Idle).value.let { uiState ->
        when (uiState) {
            is AuthUiState.Idle -> {
                RegisterContent(
                    email = email,
                    username = username,
                    name = name,
                    password = password,
                    confirmPassword = confirmPassword,
                    gender = selectedItem.value,
                    onEmailTextChanged = viewModel::changeEmailRegister,
                    onUsernameTextChanged = viewModel::changeUsernameRegister,
                    onNameTextChanged = viewModel::changeNameRegister,
                    onPasswordTextChanged = viewModel::changePasswordRegister,
                    onConfirmTextChanged = viewModel::changePasswordConfirm,
                    onGenderSelected = {
                        genderDialog.value = true
                    },
                    onRegisterClicked = {
                        viewModel.register()
                    },
                    imageUri = imageUri,
                    checkValid = dataValid,
                    onEditClicked = {
                        launcher.launch("image/*")
                    }
                )
            }
            is AuthUiState.Load -> {
                RegisterContent(
                    email = email,
                    username = username,
                    name = name,
                    password = password,
                    confirmPassword = confirmPassword,
                    gender = selectedItem.value,
                    onEmailTextChanged = { },
                    onUsernameTextChanged = { },
                    onNameTextChanged = { },
                    onPasswordTextChanged = { },
                    onConfirmTextChanged = { },
                    onGenderSelected = { },
                    onRegisterClicked = {},
                    imageUri = imageUri,
                    checkValid = dataValid,
                    onEditClicked = { },
                    modifier = modifier.alpha(0.3f)
                )
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
                LaunchedEffect(key1 = true) {
                    Toast.makeText(context, "Register Successful", Toast.LENGTH_SHORT).show()
                    navHostController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }
                }
            }
            is AuthUiState.Failure -> {
                RegisterContent(
                    email = email,
                    username = username,
                    name = name,
                    password = password,
                    confirmPassword = confirmPassword,
                    gender = selectedItem.value,
                    onEmailTextChanged = viewModel::changeEmailRegister,
                    onUsernameTextChanged = viewModel::changeUsernameRegister,
                    onNameTextChanged = viewModel::changeNameRegister,
                    onPasswordTextChanged = viewModel::changePasswordRegister,
                    onConfirmTextChanged = viewModel::changePasswordConfirm,
                    onGenderSelected = {
                        genderDialog.value = true
                    },
                    onRegisterClicked = {
                        viewModel.register()
                    },
                    imageUri = imageUri,
                    checkValid = dataValid,
                    onEditClicked = {
                        launcher.launch("image/*")
                    }
                )
                LaunchedEffect(key1 = true) {
                    Toast.makeText(
                        context,
                        "Failed please check if input correct, or check your internet",
                        Toast.LENGTH_SHORT
                    ).show()
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
    gender: String,
    onEmailTextChanged: (String) -> Unit,
    onUsernameTextChanged: (String) -> Unit,
    onNameTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    onConfirmTextChanged: (String) -> Unit,
    onGenderSelected: () -> Unit,
    onRegisterClicked: () -> Unit,
    imageUri: Uri?,
    checkValid: MutableList<Boolean>,
    onEditClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    checkValid[0] = email.isNotBlank() && isEmailValid(email)
    checkValid[1] = username.isNotBlank()
    checkValid[2] = name.isNotBlank()
    checkValid[3] = password == confirmPassword && password.isNotBlank()
    checkValid[4] = gender.isNotBlank()
    val allValid = checkValid.all { it }

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.page_background1),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopCenter
            )
            .padding(top = 50.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val context = LocalContext.current
        val initialBitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.profile_picture
        )

        Text(
            text = "Register",
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(start = 30.dp, bottom = 10.dp)
        )

        Box(modifier = modifier) {
            Image(
                bitmap =
                if (imageUri != Uri.EMPTY) {
                    val inputStream = imageUri?.let { context.contentResolver.openInputStream(it) }
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    bitmap.asImageBitmap()
                } else {
                    initialBitmap.asImageBitmap()
                },
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(136.dp)
                    .clip(CircleShape)
            )

            Button(
                onClick = onEditClicked,
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
                textColor = if (checkValid[0]) {
                    Color.Black
                } else {
                    MaterialTheme.colors.error
                },
            ),
            placeholder = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 36.dp, end = 36.dp)
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
            placeholder = { Text(text = "Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 36.dp, end = 36.dp)
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
            placeholder = { Text(text = "Display Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 36.dp, end = 36.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )



        Button(
            onClick = onGenderSelected,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            elevation = ButtonDefaults.elevation(0.dp)
        )
        {
            TextField(
                value = gender.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                },
                enabled = false,
                onValueChange = {},
                maxLines = 1,
                placeholder = { Text(text = "Choose Gender") },
                shape = RoundedCornerShape(16.dp),
                leadingIcon = {
                    if (gender == "male") {
                        Icon(Icons.Filled.Male, contentDescription = null)
                    } else {
                        Icon(Icons.Filled.Female, contentDescription = null)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    disabledLeadingIconColor = MaterialTheme.colors.primary,
                    disabledIndicatorColor = MaterialTheme.colors.secondary,
                    disabledTextColor = MaterialTheme.colors.primary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            )
        }

        TextField(
            value = password,
            onValueChange = onPasswordTextChanged,
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                leadingIconColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                disabledPlaceholderColor = Color.Gray,
                textColor = Color.Black,
            ),
            placeholder = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 36.dp, end = 36.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )

        TextField(
            value = confirmPassword,
            onValueChange = onConfirmTextChanged,
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm Password") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                leadingIconColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                placeholderColor = Color.Gray,
                textColor = if (checkValid[3]) {
                    Color.Black
                } else {
                    MaterialTheme.colors.error
                },
            ),
            placeholder = { Text(text = "Confirm Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 36.dp, end = 36.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )

        Button(
            onClick = onRegisterClicked,
            enabled = allValid,
            shape = RoundedCornerShape(16.dp),
            colors = if (allValid) {
                ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            } else {
                ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
            },
            modifier = Modifier
                .padding(top = 24.dp, bottom = 16.dp)
                .width(120.dp)
                .height(48.dp),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier =
                if (allValid) {
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
            ) {
                Text(text = "Register", color = Color.White)
            }
        }
    }
}