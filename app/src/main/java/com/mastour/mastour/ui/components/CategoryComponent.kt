package com.mastour.mastour.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastour.mastour.R
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun CategoryComponent(
    name: String,
    photoUrl: String,
    modifier: Modifier = Modifier,
){
    Card(modifier = modifier
        .clip(RoundedCornerShape(8.dp))
    ){
        Box{
            AsyncImage(
                model = photoUrl,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary, blendMode = BlendMode.Softlight)
            )
            Text(
                text = name,
                style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.ExtraBold,
                    shadow = Shadow(color = Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 8f)
                ),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 4.dp, y = (-4).dp)
            )
        }
    }
}

//CategoryComponent2 Don't Delete, Needed !
@Composable
fun CategoryComponent2(
    name: String,
    photoUrl: Int,
    modifier: Modifier = Modifier,
){
    Card(modifier = modifier
        .clip(RoundedCornerShape(8.dp))
    ){
        Box{
            Image(
                painter = painterResource(photoUrl),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary, blendMode = BlendMode.Softlight)
            )
            Text(
                text = name,
                style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.ExtraBold,
                    shadow = Shadow(color = Color.Black,
                        offset = Offset(4f, 4f),
                        blurRadius = 8f)
                ),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 4.dp, y = (-4).dp)
            )
        }
    }
}
