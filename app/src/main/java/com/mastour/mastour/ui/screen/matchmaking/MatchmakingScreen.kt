package com.mastour.mastour.ui.screen.matchmaking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mastour.mastour.R
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun MatchmakingContent(
    modifier: Modifier = Modifier,
    nextOnClicked: () -> Unit,
){
    Box(modifier = modifier.fillMaxSize()){
        Image(painter = painterResource(R.drawable.match_bg),
            contentDescription = "Matchmaking",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier
            .align(Alignment.TopStart)
            .padding(16.dp)) {
            Text(text = "Personality", style = MaterialTheme.typography.h5.copy(
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold))
            Text(text = "Matchmaking", style = MaterialTheme.typography.h5.copy(
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.ExtraBold))
        }

        Column(modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(16.dp)) {
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)){
                    append("Find your best ")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colors.primary, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)){
                    append("tour guide\n")
                }
                withStyle(style = SpanStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)){
                    append("by using our ")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colors.primary, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)){
                    append("matchmaking feature\n")
                }
            })

            TextButton(onClick = nextOnClicked, modifier = Modifier.align(Alignment.End)) {
                Text(text = "Next", style = MaterialTheme.typography.h5.copy(
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold) )
                Icon(imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = "next", tint = Color.White, modifier = Modifier.size(24.dp))
            }
        }

    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_3A, showSystemUi = true)
fun MatchmakingScreenPreview(){
    MasTourTheme {
        MatchmakingContent(nextOnClicked = {})
    }
}

