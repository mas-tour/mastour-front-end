package com.mastour.mastour.ui.screen.matchmaking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.mastour.mastour.R
import com.mastour.mastour.data.remote.DataGuides
import com.mastour.mastour.ui.components.ExtendedUserComponent

@Composable
fun MatchmakingResultScreen(){

}

@Composable
fun MatchmakingResultContent2(
    modifier: Modifier = Modifier,
    userList : LazyPagingItems<DataGuides>,
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
                if (guide != null) {
                    ExtendedUserComponent(
                        name = guide.name,
                        photoUrl = guide.picture,
                        place = guide.city,
                        specialization = guide.categories,
                        price = guide.pricePerDay,
                        desc = guide.description,
                        percentage = 90,
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
}