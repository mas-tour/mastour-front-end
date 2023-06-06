package com.mastour.mastour.ui.screen.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mastour.mastour.R
import com.mastour.mastour.dummy.Guide
import com.mastour.mastour.dummy.GuideData
import com.mastour.mastour.ui.components.UserComponent2
import com.mastour.mastour.ui.navigation.CollapsedTopBar
import com.mastour.mastour.ui.navigation.ExpandedToolbar
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun CategoryScreen() {
    val listState = rememberLazyListState()

    val isCollapsed: Boolean by remember {
        derivedStateOf {
            val isFirstItemHidden =
                listState.firstVisibleItemScrollOffset > 146
            isFirstItemHidden || listState.firstVisibleItemIndex > 0
        }
    }

    // TODO: Acquire data from arguments
    CategoryContent(
        guideData = GuideData.guides,
        title = "Makassar",
        moveToGuideDetail = {},
        listState = listState,
        onBackClicked = {}, // TODO: Add navigation
        isCollapsed = isCollapsed
    )
}

@Composable
fun CategoryContent(
    title: String,
    guideData: List<Guide>, //dummy, change later
    moveToGuideDetail: (String) -> Unit,
    listState: LazyListState,
    isCollapsed: Boolean,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        CollapsedTopBar(modifier = Modifier.zIndex(2f), title = title, isCollapsed = isCollapsed)

        LazyColumn(state = listState) {
            // TODO: Change data to arguments
            item { ExpandedToolbar(title = title, picture = R.drawable.makassar, onBackClicked = onBackClicked) }
            items(guideData) { guide ->
                UserComponent2( // TODO: still using preview component, to be changed
                    name = guide.name,
                    photoUrl = guide.photoUrl,
                    place = guide.place,
                    specialization = guide.specialization,
                    price = guide.price,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(horizontal = 16.dp, vertical = 5.dp)
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

// Preview is not working for some reason... Run the app instead
@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun CategoryScreenPreview(){
    MasTourTheme {
        CategoryContent(
            guideData = GuideData.guides,
            title = "Makassar",
            moveToGuideDetail = {},
            listState = rememberLazyListState(),
            onBackClicked = {},
            isCollapsed = false
        )
    }
}