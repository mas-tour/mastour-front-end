package com.mastour.mastour.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastour.mastour.data.remote.CategoriesItem
import com.mastour.mastour.util.formatNumber

@Composable
fun UserComponent(
    name: String,
    photoUrl: String,
    place: String,
    specialization: List<CategoriesItem>,
    price: Long,
    modifier: Modifier = Modifier
) {
    Card(shape = RoundedCornerShape(16.dp), modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(98.dp)
                    .width(128.dp)
                    .padding(end = 16.dp, start = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.padding(bottom = 8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                TagComponent(
                    name = place,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(end = 4.dp)
                )
                LazyRow(modifier = Modifier
                    .padding(end = 8.dp, top = 4.dp)){
                    items(items = specialization, key = { it.id }) {categories ->
                        TagComponent(
                            name = categories.name,
                            color = MaterialTheme.colors.secondaryVariant,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = "IDR " + formatNumber(price) + " / Day",
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colors.primary,
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ExtendedUserComponent(
    name: String,
    photoUrl: String,
    place: String,
    specialization: List<CategoriesItem>,
    price: Long,
    desc: String,
    percentage: Double,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(shape = RoundedCornerShape(16.dp), modifier = modifier) {
        Column(Modifier.padding(top = 16.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceEvenly) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(98.dp)
                        .width(128.dp)
                        .padding(end = 16.dp, start = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.ExtraBold),
                        modifier = Modifier.padding(bottom = 8.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    TagComponent(
                        name = place,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    LazyRow(modifier = Modifier
                        .padding(end = 8.dp, top = 4.dp)){
                        items(items = specialization, key = { it.id }) {categories ->
                            TagComponent(
                                name = categories.name,
                                color = MaterialTheme.colors.secondaryVariant,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                    }
                    Text(
                        text = "IDR " + formatNumber(price) + " / Day",
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colors.primary,
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = desc,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(end = 16.dp, start = 8.dp, top = 16.dp, bottom = 16.dp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.size(8.dp).weight(1F))
                TagComponent(
                    name = "${percentage.toInt()}%", color = color, modifier = Modifier
                        .size(98.dp)
                        .padding(16.dp)
                        .align(Alignment.Bottom)
                )
            }
        }
    }
}