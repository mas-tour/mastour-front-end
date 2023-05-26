package com.mastour.mastour.ui.screen.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastour.mastour.R
import com.mastour.mastour.dummy.OrderData
import com.mastour.mastour.dummy.OrderDummy
import com.mastour.mastour.ui.components.OrderComponent
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun HistoryScreen(modifier: Modifier = Modifier) {

}

@Composable
fun HistoryContent(
    orderData: List<OrderDummy>,
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = modifier.padding(top = 125.dp)
        ) {
            items(orderData) { order ->
                OrderComponent(
                    guideName = order.guideName,
                    location = order.location,
                    status = order.status,
                    price = order.price,
                    duration = order.duration,
                    photoUrl = order.photoUrl
                )
            }
        }
    }

    Text(text = "History",
        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier
            .padding(top = 60.dp, start = 30.dp)
    )
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
fun HistoryScreenPreview() {
    MasTourTheme {
        HistoryContent(orderData = OrderData.orders)
    }
}

@Composable
@Preview(showBackground = true)
fun OrderComponentPreview() {
    MasTourTheme() {
        OrderComponent(
            guideName = "M. Thariq Dorong",
            location = "Bandung",
            status = "complete",
            price = 200000,
            duration = 8,
            photoUrl = R.drawable.dummy_user
        )
    }
}