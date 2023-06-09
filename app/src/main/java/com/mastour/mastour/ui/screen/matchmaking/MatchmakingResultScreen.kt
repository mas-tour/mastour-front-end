package com.mastour.mastour.ui.screen.matchmaking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastour.mastour.R
import com.mastour.mastour.dummy.Guide
import com.mastour.mastour.dummy.GuideData
import com.mastour.mastour.ui.components.ExtendedUserComponent2
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun MatchmakingResultScreen(){

}

@Composable
fun MatchmakingResultContent2(
    modifier: Modifier = Modifier,
    userList : List<Guide>,
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.page_background1),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopCenter
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Results",
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(top = 60.dp, start = 16.dp)
                .align(Alignment.Start)
        )
        LazyColumn(
            modifier = modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            items(userList){guide ->
                ExtendedUserComponent2(
                    name = guide.name,
                    photoUrl = guide.photoUrl,
                    place = guide.place,
                    specialization = guide.specialization,
                    price = guide.price,
                    desc = guide.desc,
                    percentage = guide.percentage,
                    color = Color.Green,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_3A)
fun MatchmakingResultPreview(){
    MasTourTheme {
        MatchmakingResultContent2(userList = GuideData.guides)
    }
}