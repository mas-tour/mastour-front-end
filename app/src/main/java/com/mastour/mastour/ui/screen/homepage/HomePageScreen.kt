package com.mastour.mastour.ui.screen.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mastour.mastour.R
import com.mastour.mastour.data.remote.CategoriesItem
import com.mastour.mastour.ui.components.CategoryComponent
import com.mastour.mastour.ui.components.CategoryComponent2
import com.mastour.mastour.ui.navigation.HomeTopBar
import com.mastour.mastour.ui.navigation.Screen
import com.mastour.mastour.ui.viewmodel.HomeViewModel
import com.mastour.mastour.util.UiState

@Composable
fun HomePageScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navHostController: NavHostController
){
    Scaffold(
        topBar = {
            HomeTopBar()
        })
    {paddingValues ->
        viewModel.categoriesResponse.collectAsState(initial = UiState.Loading).value.let {uiState->
            when(uiState){
                is UiState.Loading ->{
                    viewModel.getCategories()
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Loading")
                        CircularProgressIndicator(color = Color.Black)
                    }
                }
                is UiState.Success ->{
                    uiState.data?.citiesResponse?.let {
                        uiState.data.specResponse.let { it1 ->
                            HomePageContent(
                                moveToCategoryDetail = {},
                                moveToMatchMaking = { navHostController.navigate(Screen.Matchmaking.route) {
                                    popUpTo(Screen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }},
                                placeData = it.data,
                                categoryData = it1.data,
                                modifier = modifier.padding(paddingValues)
                            )
                        }
                    }
                }
                is UiState.Failure ->{
                    //TODO
                }
            }
        }
    }
}
@Composable
fun HomePageContent(
    moveToCategoryDetail: (String) -> Unit,
    moveToMatchMaking: () -> Unit,
    placeData: List<CategoriesItem>,
    categoryData: List<CategoriesItem>,
    modifier: Modifier = Modifier,
){
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                CategoryComponent(name = place.name, photoUrl = place.picture, modifier = Modifier
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
                    photoUrl = category.picture,
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
