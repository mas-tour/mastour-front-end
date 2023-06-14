package com.mastour.mastour.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastour.mastour.R
import com.mastour.mastour.ui.theme.md_theme_dark_primaryContainer
import com.mastour.mastour.ui.theme.md_theme_dark_secondary


@Composable
fun HomeTopBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        md_theme_dark_primaryContainer,
                        md_theme_dark_secondary
                    )
                )
            )
            .fillMaxWidth()
            .height(56.dp)
            .padding(16.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Image(
            painter = painterResource(id = R.drawable.wayang_only),
            contentDescription = "Makassar Image",
            modifier = Modifier
                .scale(2.3f)
                .padding(start = 3.dp)
        )
    }
}

@Composable
fun SearchTopBar(
    search: String,
    onSearchTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column() {
        Box(
            modifier = modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            md_theme_dark_primaryContainer,
                            md_theme_dark_secondary
                        )
                    )
                )
                .fillMaxWidth()
                .height(56.dp)
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {}
        TextField(
            value = search,
            onValueChange = onSearchTextChanged,
            shape = RoundedCornerShape(16.dp),
            maxLines = 1,
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                leadingIconColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                placeholderColor = Color.Gray,
                textColor = Color.Black,
            ),
            placeholder = { Text(text = "Search") },
            modifier = modifier
                .fillMaxWidth()
                .offset(y = (-20).dp)
                .padding(horizontal = 15.dp)
                .shadow(elevation = 16.dp)
        )
    }
}


@Preview
@Composable
private fun TopBarPreview() {
    HomeTopBar()
}