package com.mastour.mastour.ui.screen.register

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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastour.mastour.R
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun RegisterScreen(modifier: Modifier = Modifier) {

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
        Box(modifier = modifier) {
            Image(
                painter = painterResource(R.drawable.profile_picture),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.FillHeight,
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

    IconButton(onClick = onBackClicked) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Go Back"
        )
    }

    Text(text = "Register",
        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(top = 60.dp, start = 30.dp)
    )
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun RegisterScreenPreview() {
    MasTourTheme {
        RegisterContent(username = "", name = "", email = "", password = "", confirmPassword = "",
            onEmailTextChanged = {}, onPasswordTextChanged = {}, onNameTextChanged = {},
            onConfirmTextChanged = {}, onUsernameTextChanged = {}, onRegisterClicked = {},
            onBackClicked = {}, onEditClicked = {})
    }
}