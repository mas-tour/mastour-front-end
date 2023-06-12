package com.mastour.mastour.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastour.mastour.R
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun OrderComponent(
    guideName: String,
    location: String,
    status: String,
    price: Int,
    duration: Int,
    photoUrl: String,
    modifier: Modifier = Modifier
){
    Card(shape = RoundedCornerShape(16.dp), modifier = modifier
        .width(350.dp)
        .height(90.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = modifier.fillMaxWidth()
        ) {
            AsyncImage(model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(75.dp)
                    .width(105.dp)
                    .padding(end = 16.dp, start = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column {
                Text(
                    text = guideName,
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.ExtraBold),
                    modifier = modifier,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Row (modifier = Modifier.padding(end = 8.dp)){
                    Text(
                        text = "$location - $duration Days(s)",
                        style = MaterialTheme.typography.subtitle2.copy(
                            color = Color.Gray,
                            fontWeight = FontWeight.Normal)
                    )
                }

                Row(Modifier.padding(top = 15.dp)) {
                    Text(
                        text = "IDR $price",
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colors.primary,
                        )
                    )
                    Spacer(modifier.weight(1f))

                    // TODO: Change this logic later
                    val tagColor = if (status == "complete") {
                        Color(0x9996EB9E)
                    } else {
                        Color(0x99FBE949)
                    }
                    TagComponent(
                        name = status,
                        color = tagColor,
                        modifier = Modifier
                            .padding(end = 4.dp)

                    )
                }
            }
        }
    }
}
