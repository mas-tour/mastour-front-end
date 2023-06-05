package com.mastour.mastour.ui.screen.homepage

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastour.mastour.MainJetpack
import com.mastour.mastour.R
import com.mastour.mastour.dummy.CategoryData
import com.mastour.mastour.dummy.CategoryData2
import com.mastour.mastour.dummy.CategoryDatas2
import com.mastour.mastour.ui.components.CategoryComponent
import com.mastour.mastour.ui.components.CategoryComponent2
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun HomePageScreen(modifier: Modifier = Modifier){
    HomePageContent2(
        moveToCategoryDetail = {},
        moveToMatchMaking = {},
        placeData = CategoryDatas2.place,
        categoryData = CategoryDatas2.category,
    )
}
@Composable
fun HomePageContent(
    moveToCategoryDetail: (String) -> Unit,
    moveToMatchMaking: () -> Unit,
    placeData: List<CategoryData>,
    categoryData: List<CategoryData>,
    modifier: Modifier = Modifier,
){
    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.wayang_logo),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        )
        Text(
            text = "Popular Places",
            style = MaterialTheme.typography.h5.copy(
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp))

        LazyRow(modifier = Modifier.padding(top = 8.dp)){
            items(placeData){place ->
                CategoryComponent(name = place.name, photoUrl = place.photoUrl, modifier = Modifier
                    .size(160.dp)
                    .padding(8.dp)
                    .clickable {
                        moveToCategoryDetail(place.id)
                    }
                )
            }
        }
        CategoryComponent2(
            name = "Use Our Matchmaking Technology",
            photoUrl = R.drawable.match_bg,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(16.dp)
                .clickable {
                    moveToMatchMaking()
                }
        )

        Text(
            text = "Categories",
            style = MaterialTheme.typography.h5.copy(
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp))

        LazyRow(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)){
            items(categoryData){ category ->
                CategoryComponent(
                    name = category.name,
                    photoUrl = category.photoUrl,
                    modifier = Modifier
                        .size(160.dp)
                        .padding(8.dp)
                        .clickable {
                            moveToCategoryDetail(category.id)
                        }
                )
            }
        }
    }
}

//HomePageContent2 only for drawable preview (To Be Deleted)
@Composable
fun HomePageContent2(
    moveToCategoryDetail: (String) -> Unit,
    moveToMatchMaking: () -> Unit,
    placeData: List<CategoryData2>,
    categoryData: List<CategoryData2>,
    modifier: Modifier = Modifier,
){
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.wayang_logo),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .size(200.dp)
                .padding(16.dp)
        )
        Text(
            text = "Popular Places",
            style = MaterialTheme.typography.h5.copy(
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp))

        LazyRow(modifier = Modifier.padding(top = 8.dp)){
            items(placeData){place ->
                CategoryComponent2(name = place.name, photoUrl = place.photoUrl, modifier = Modifier
                    .size(160.dp)
                    .padding(8.dp)
                    .clickable {
                        moveToCategoryDetail(place.id)
                    }
                )
            }
        }
        CategoryComponent2(
            name = "Use Our Matchmaking Technology",
            photoUrl = R.drawable.match_bg,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .padding(16.dp)
                .clickable {
                    moveToMatchMaking()
                },
        )

        Text(
            text = "Categories",
            style = MaterialTheme.typography.h5.copy(
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold),
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp))

        LazyRow(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)){
            items(categoryData){ category ->
                CategoryComponent2(
                    name = category.name,
                    photoUrl = category.photoUrl,
                    modifier = Modifier
                        .size(160.dp)
                        .padding(8.dp)
                        .clickable {
                            moveToCategoryDetail(category.id)
                        }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_3A, showSystemUi = true)
@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
fun HomePageScreenPreview(){
    MasTourTheme {
        HomePageContent2(
            moveToCategoryDetail = {},
            moveToMatchMaking = {},
            placeData = CategoryDatas2.place,
            categoryData = CategoryDatas2.category,
        )
    }
}
