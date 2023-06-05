package com.mastour.mastour.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastour.mastour.dummy.Guide
import com.mastour.mastour.dummy.GuideData
import com.mastour.mastour.ui.components.UserComponent2
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    SearchContent(
        search = "",
        onSearchTextChanged = {},
        moveToGuideDetail = {},
        guideData = GuideData.guides,
        result = GuideData.guides.size,
    )
}

@Composable
fun SearchContent(
    guideData: List<Guide>, //dummy, change later
    result: Int,
    search: String,
    onSearchTextChanged: (String) -> Unit,
    moveToGuideDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // TODO: Add scroll?
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
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
                .padding(16.dp)
                .shadow(elevation = 16.dp)
        )

        Box(modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp)) {
            val text = buildAnnotatedString {
                append("Displaying ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("$result results")
                }
            }
            Text(text)
        }
        
        LazyColumn {
            items(guideData) {guide ->
                UserComponent2( // TODO: still using preview component, to be changed
                    name = guide.name,
                    photoUrl = guide.photoUrl,
                    place = guide.place,
                    specialization = guide.specialization,
                    price = guide.price,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(16.dp)
                        .shadow(
                            elevation = 8.dp,
                            ambientColor = MaterialTheme.colors.primary,
                            spotColor = MaterialTheme.colors.primary
                        )
                        .clickable {
                            moveToGuideDetail(guide.id)
                        }
                )
            }
        }

    }

}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
fun SearchScreenPreview(){
    MasTourTheme {
        SearchContent(
            search = "",
            onSearchTextChanged = {},
            moveToGuideDetail = {},
            guideData = GuideData.guides,
            result = GuideData.guides.size,
        )
    }
}

