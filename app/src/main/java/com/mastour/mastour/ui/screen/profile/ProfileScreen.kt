package com.mastour.mastour.ui.screen.profile

import android.app.DatePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.mastour.mastour.data.local.UserData
import com.mastour.mastour.ui.navigation.Screen
import com.mastour.mastour.ui.screen.dialog.GenderSelectionDialog
import com.mastour.mastour.ui.screen.dialog.PhoneNumberDialog
import com.mastour.mastour.ui.viewmodel.ProfileViewModel
import com.mastour.mastour.util.UiState
import com.mastour.mastour.util.getAgeFromTimestamp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    val userExist by viewModel.userExist
    val phoneNumber by viewModel.phoneNumber

    SideEffect {
        viewModel.tryUserExist()
        viewModel.tryUserToken()
    }

    LaunchedEffect(userExist){
        if(!userExist) {
            navHostController.navigate(Screen.Login.route) {
                popUpTo(0)
            }
        }
        viewModel.getProfile()
    }

    val context = LocalContext.current

    // Date picker UI
    val datePicker = DatePickerDialog(context)
    val calendar = Calendar.getInstance()
    datePicker.datePicker.maxDate = calendar.timeInMillis
    calendar.add(Calendar.YEAR, -100)
    datePicker.datePicker.minDate = calendar.timeInMillis
    datePicker.setOnDateSetListener { _, year, month, dayOfMonth ->
        val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
        val localDateTime = LocalDateTime.of(selectedDate, LocalTime.MIDNIGHT)
        val timestamp = localDateTime.toEpochSecond(ZoneOffset.UTC)
        viewModel.changeBirthDate(timestamp)
        viewModel.putBirthDate()
    }

    // GenderDialog UI
    val genderDialog = remember { mutableStateOf(false) }
    val genders = listOf("male", "female")
    val selectedItem = remember {
        mutableStateOf(genders[0])
    }
    GenderSelectionDialog(
        openDialog = genderDialog,
        selectedItem = selectedItem,
        genderOptions = genders,
        onSubmitClicked = {
            viewModel.changeGender(selectedItem.value)
            viewModel.putGender()
            genderDialog.value = false
        }
    )

    // PhoneNumberDialog UI
    val phoneNumberDialog = remember { mutableStateOf(false) }
    PhoneNumberDialog(openDialog = phoneNumberDialog,
        phoneNumber = phoneNumber,
        onPhoneNumberChanged = viewModel::changeNumber,
        onSubmitClicked = {
            viewModel.putNumber()
            phoneNumberDialog.value = false
        }
    )

    viewModel.profileResponse.collectAsState(initial = UiState.Loading).value.let { UiState ->
        when(UiState) {
            is UiState.Loading -> {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Profile Loading")
                        CircularProgressIndicator(color = Color.Black)
                    }
            }

            is UiState.Success -> {
                val user = UiState.data?.profileBody
                user?.let {
                    val data = UserData(
                        username = it.username,
                        email = it.email,
                        name = it.name,
                        phoneNumber = it.phoneNumber,
                        gender = it.gender,
                        birthDate = it.birthDate.toLong(),
                        picture = it.picture
                    )
                    viewModel.changeUserData(data)

                    ProfileContent(name = it.name,
                        username = it.username,
                        gender = it.gender,
                        timestamp = it.birthDate.toLong(),
                        photoUrl = it.picture,
                        phoneNumber = it.phoneNumber,
                        onGenderClicked = {
                            genderDialog.value = true
                        },
                        onPhoneNumberClicked = {
                            phoneNumberDialog.value = true
                        },
                        onAgeClicked = {
                            datePicker.show()
                        },
                        onLogoutClicked =
                        {
                            viewModel.deleteSession()
                        }
                    )
                }
            }
            is UiState.Failure -> {
                // TODO: Toast or dialogue, Register failed
                // TODO: find a better way of handling errors...
                rememberCoroutineScope().launch {
                    Toast.makeText(context, UiState.e?.message, Toast.LENGTH_SHORT).show()
                }
                viewModel.deleteSession()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileContent(
    name: String,
    username: String,
    gender: String,
    timestamp: Long,
    photoUrl: String,
    phoneNumber: String,
    onGenderClicked: () -> Unit,
    onAgeClicked: () -> Unit,
    onPhoneNumberClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = modifier
            .fillMaxWidth()
            .height(300.dp)) {

            AsyncImage(
                model = photoUrl, // TODO: Dummy, Change this later
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color(0xFF7147B1).copy(alpha = 0.3f), blendMode = BlendMode.Color),
                modifier = Modifier
                    .fillMaxWidth()
                    .blur(2.dp)
            )
            AsyncImage(
                model = photoUrl,
                contentDescription = "Profile Picture",
                contentScale = ContentScale.FillHeight,
                modifier = modifier
                    .size(136.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = 36.dp)
                    .border(
                        BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                        CircleShape
                    )
                    .shadow(3.dp, shape = CircleShape)
                    .clip(CircleShape)
            )
        }

        Text(name,
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colors.primary,
            modifier = modifier.padding(top = 47.dp))
        Text(username.lowercase(),
            style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = modifier
                .padding(horizontal = 15.dp)
                .padding(top = 25.dp)) {

            // Menu
            Button(
                onClick = onAgeClicked,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                shape = RoundedCornerShape(30.dp),
                contentPadding = PaddingValues(10.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .advancedShadow(shadowRadius = 3.dp, borderRadius = 30.dp, offsetY = 3.dp)
            )
            {
                val age = getAgeFromTimestamp(timestamp)
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(horizontal = 15.dp)) {
                    Icon(Icons.Filled.CalendarToday, contentDescription = "Age")
                    Spacer(modifier.width(14.dp))
                    Text(text =
                    if (timestamp == 0L) {
                        "[click here to set]"
                    } else {
                        "$age years old"
                    }, style = MaterialTheme.typography.subtitle2)
                    Spacer(modifier.weight(1f))
                }
            }

            Button(
                onClick = onGenderClicked,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                shape = RoundedCornerShape(30.dp),
                contentPadding = PaddingValues(10.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .advancedShadow(shadowRadius = 3.dp, borderRadius = 30.dp, offsetY = 3.dp)
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(horizontal = 15.dp)) {
                    Icon(Icons.Filled.Person, contentDescription = "Gender")
                    Spacer(modifier.width(14.dp))
                    Text(gender.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        style = MaterialTheme.typography.subtitle2)
                    Spacer(modifier.weight(1f))
                }
            }

            Button(
                onClick = onPhoneNumberClicked,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                shape = RoundedCornerShape(30.dp),
                contentPadding = PaddingValues(10.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .advancedShadow(shadowRadius = 3.dp, borderRadius = 30.dp, offsetY = 3.dp)
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(horizontal = 15.dp)) {
                    Icon(Icons.Filled.Phone, contentDescription = "Phone Number")
                    Spacer(modifier.width(14.dp))
                    Text(
                        if (phoneNumber == "") {
                            "[click here to set]"
                        } else {
                            phoneNumber
                        }
                        , style = MaterialTheme.typography.subtitle2)
                    Spacer(modifier.weight(1f))
                }
            }

            Button(
                onClick = onLogoutClicked,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                shape = RoundedCornerShape(30.dp),
                contentPadding = PaddingValues(10.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .advancedShadow(shadowRadius = 3.dp, borderRadius = 30.dp, offsetY = 3.dp)
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(horizontal = 15.dp)) {
                    Icon(Icons.Filled.Logout, contentDescription = "Logout", tint = Color(0XFFB14747))
                    Spacer(modifier.width(14.dp))
                    Text("Logout",
                        style = MaterialTheme.typography.subtitle2,
                        color = Color(0XFFB14747))
                    Spacer(modifier.weight(1f))
                }
            }
        }
    }
}

// code snippet
fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 5.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = composed {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparent = color.copy(alpha= 0f).toArgb()

    this.drawBehind {

        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparent

            frameworkPaint.setShadowLayer(
                shadowRadius.toPx(),
                offsetX.toPx(),
                offsetY.toPx(),
                shadowColor
            )
            it.drawRoundRect(
                0f,
                0f,
                this.size.width,
                this.size.height,
                borderRadius.toPx(),
                borderRadius.toPx(),
                paint
            )
        }
    }
}