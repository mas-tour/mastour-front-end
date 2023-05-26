package com.mastour.mastour.ui.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mastour.mastour.R
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {

}

@Composable
fun ProfileContent(
    name: String,
    username: String,
    gender: String,
    age: String,
    photoUrl: Int, // TODO: Change Later
    phoneNumber: String,
    onGenderClicked: () -> Unit,
    onAgeClicked: () -> Unit,
    onPhoneNumberClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = modifier
            .fillMaxWidth()
            .height(300.dp)) {

            Image(
                painter = painterResource(photoUrl), // TODO: Dummy, Change this later
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color(0xFF7147B1).copy(alpha = 0.3f), blendMode = BlendMode.Color),
                modifier = Modifier
                    .fillMaxWidth()
                    .blur(
                        2.dp,
                        edgeTreatment = BlurredEdgeTreatment(
                            RoundedCornerShape(
                                bottomEndPercent = 10,
                                bottomStartPercent = 10
                            )
                        )
                    )
            )
            Image(
                painter = painterResource(photoUrl),
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
                .padding(top = 50.dp)) {

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
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(horizontal = 15.dp)) {
                    Icon(Icons.Filled.CalendarToday, contentDescription = "Age")
                    Spacer(modifier.width(14.dp))
                    Text("$age years old")
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
                    Text(gender) // TODO: Dummy, change later
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
                    Text(phoneNumber)
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
                    Text("Logout", color = Color(0XFFB14747)) // TODO: maybe include in Color.kt
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

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
fun SearchScreenPreview(){
    MasTourTheme {
        ProfileContent(name = "Bagus Wijaya",
            username = "bagus123",
            gender = "Male",
            age = "27",
            photoUrl = R.drawable.dummy_user,
            phoneNumber = "08234623232",
            onGenderClicked = {},
            onPhoneNumberClicked = {},
            onAgeClicked = {},
            onLogoutClicked = {}
        )
    }
}