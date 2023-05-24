package com.mastour.mastour.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun TagComponent(
    name: String,
    color: Color,
    modifier: Modifier = Modifier,
){
    Box(contentAlignment = Alignment.Center, modifier = modifier
        .clip(shape = RoundedCornerShape(8.dp))
        .background(color = color)){
        Row {
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = name, style = MaterialTheme.typography.caption)
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TagComponentPreview(){
    MasTourTheme {
        TagComponent(name = "Horror Attraction", color = MaterialTheme.colors.secondaryVariant)
    }
}

@Composable
@Preview(showBackground = true)
fun TagComponentPreview2(){
    MasTourTheme {
        TagComponent(name = "Kediri", color = MaterialTheme.colors.primaryVariant)
    }
}