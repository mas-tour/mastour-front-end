package com.mastour.mastour.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastour.mastour.ui.theme.MasTourTheme
import com.mastour.mastour.R
import com.mastour.mastour.data.remote.CategoriesItem

@Composable
fun UserComponent(
    name: String,
    photoUrl: String,
    place: String,
    specialization: List<CategoriesItem>,
    price: Long,
    modifier: Modifier = Modifier
){
    Card(shape = RoundedCornerShape(16.dp), modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
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
                TagComponent(name = place, color = MaterialTheme.colors.primaryVariant, modifier = Modifier.padding(end = 4.dp))
                Row (modifier = Modifier
                    .padding(end = 8.dp, top = 4.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    )){
                    TagComponent(name = specialization[0].name, color = MaterialTheme.colors.secondaryVariant, modifier = Modifier.padding(end = 4.dp))
                    TagComponent(name = specialization[1].name, color = MaterialTheme.colors.secondaryVariant, modifier = Modifier.padding(end = 4.dp))
                    TagComponent(name = specialization[2].name, color = MaterialTheme.colors.secondaryVariant, modifier = Modifier.padding(end = 4.dp))
                }
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = "IDR $price",
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
    percentage: Int,
    color: Color,
    modifier: Modifier = Modifier
){
    Card(shape = RoundedCornerShape(16.dp), modifier = modifier) {
        Column {
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
                    TagComponent(name = place, color = MaterialTheme.colors.primaryVariant, modifier = Modifier.padding(end = 4.dp))
                    Row (modifier = Modifier
                        .padding(end = 8.dp, top = 4.dp)
                        .horizontalScroll(
                            rememberScrollState()
                        )){
                        TagComponent(name = specialization[0].name, color = MaterialTheme.colors.secondaryVariant, modifier = Modifier.padding(end = 4.dp))
                        TagComponent(name = specialization[1].name, color = MaterialTheme.colors.secondaryVariant, modifier = Modifier.padding(end = 4.dp))
                        TagComponent(name = specialization[2].name, color = MaterialTheme.colors.secondaryVariant, modifier = Modifier.padding(end = 4.dp))
                    }

                    Text(
                        text = "IDR $price",
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colors.primary,
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = desc,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .width(240.dp)
                        .padding(end = 16.dp, start = 8.dp, top = 16.dp, bottom = 16.dp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.weight(1F))
                TagComponent(name = "$percentage%", color = color, modifier = Modifier
                    .size(98.dp)
                    .padding(16.dp)
                    .align(Alignment.Bottom))
            }
        }
    }
}
//UserComponent2 Drawable Preview Only (To Be Deleted)
@Composable
fun UserComponent2(
    name: String,
    photoUrl: Int,
    place: String,
    specialization: String,
    price: Int,
    modifier: Modifier = Modifier
){
    Card(shape = RoundedCornerShape(16.dp), modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
            Image(painter = painterResource(photoUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(98.dp)
                    .width(128.dp)
                    .padding(end = 16.dp, start = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.padding(bottom = 8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Row (modifier = Modifier.padding(end = 8.dp)){
                    TagComponent(name = place, color = MaterialTheme.colors.primaryVariant, modifier = Modifier.padding(end = 4.dp))
                    TagComponent(name = specialization, color = MaterialTheme.colors.secondaryVariant)
                }
                Text(
                    text = "IDR $price",
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
fun ExtendedUserComponent2(
    name: String,
    photoUrl: Int,
    place: String,
    specialization: List<CategoriesItem>,
    price: Int,
    desc: String,
    percentage: Int,
    color: Color,
    modifier: Modifier = Modifier
){
    Card(shape = RoundedCornerShape(16.dp), modifier = modifier) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(photoUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(98.dp)
                        .width(128.dp)
                        .padding(end = 16.dp, start = 8.dp, top = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Column {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.ExtraBold),
                        modifier = Modifier.padding(bottom = 8.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    TagComponent(name = place, color = MaterialTheme.colors.primaryVariant, modifier = Modifier.padding(end = 4.dp))
                    Row (modifier = Modifier
                        .padding(end = 8.dp, top = 4.dp)
                        .horizontalScroll(
                            rememberScrollState()
                        )){
                        TagComponent(name = specialization[0].name, color = MaterialTheme.colors.secondaryVariant, modifier = Modifier.padding(end = 4.dp))
                        TagComponent(name = specialization[1].name, color = MaterialTheme.colors.secondaryVariant, modifier = Modifier.padding(end = 4.dp))
                        TagComponent(name = specialization[2].name, color = MaterialTheme.colors.secondaryVariant, modifier = Modifier.padding(end = 4.dp))
                    }
                    Text(
                        text = "IDR $price",
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colors.primary,
                        ),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start, 
                modifier = Modifier.fillMaxWidth()
            ){
                Text(text = desc,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .width(240.dp)
                        .padding(end = 16.dp, start = 8.dp, top = 16.dp, bottom = 16.dp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.weight(1F))
                TagComponent(name = "$percentage%", color = color, modifier = Modifier
                    .size(98.dp)
                    .padding(16.dp)
                    .align(Alignment.Bottom))
            }
        }
    }
}

@Composable
@Preview(showBackground = false)
fun UserComponentPreview(){
    MasTourTheme {
        UserComponent2(name = "Borobudur", photoUrl = R.drawable.asylum,
            place = "Kediri",
            specialization = "Horror Attraction",
            price = 200000,
            modifier = Modifier
                .fillMaxWidth()
                .height(128.dp))
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun UserComponentPreview2(){
    MasTourTheme {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            UserComponent2(
                name = "Bagus Wijaya",
                photoUrl = R.drawable.dummy_user,
                place = "Bandung" ,
                specialization = "Sightseeing",
                price = 200000,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .shadow(
                        elevation = 8.dp,
                        ambientColor = MaterialTheme.colors.primary,
                        spotColor = MaterialTheme.colors.primary
                    )
            )
        }
    }
}