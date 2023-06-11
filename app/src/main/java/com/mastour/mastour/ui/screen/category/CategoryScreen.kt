package com.mastour.mastour.ui.screen.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mastour.mastour.data.remote.DataGuides
import com.mastour.mastour.ui.components.UserComponent
import com.mastour.mastour.ui.navigation.CollapsedTopBar
import com.mastour.mastour.ui.navigation.ExpandedToolbar
import com.mastour.mastour.ui.viewmodel.GuidesViewModel
import com.mastour.mastour.util.UiState

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    isCity: Boolean,
    id: String,
    moveToGuideDetail: (String) -> Unit,
    viewModel: GuidesViewModel = hiltViewModel(),
    onBackClicked: () -> Unit

) {
    val listState = rememberLazyListState()

    val isCollapsed: Boolean by remember {
        derivedStateOf {
            val isFirstItemHidden =
                listState.firstVisibleItemScrollOffset > 146
            isFirstItemHidden || listState.firstVisibleItemIndex > 0
        }
    }
    viewModel.categoriesResponse.collectAsState(initial = UiState.Loading).value.let { uiState->
        when(uiState){
            is UiState.Loading ->{
                viewModel.getCategories()
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Loading")
                    CircularProgressIndicator(color = Color.Black)
                }
            }
            is UiState.Success ->{
               val dataCity =  uiState.data?.citiesResponse?.data?.filter {
                    it.id == id }
                val dataCategory = uiState.data?.specResponse?.data?.filter {
                    it.id == id
                }
                (if (isCity && dataCity != null) { dataCity[0] } else{
                    dataCategory?.get(0)
                })?.let {
                    CategoryContent(
                        title =
                        it.name,
                        cityPic = it.picture,
                        guideData =
                        if(isCity){viewModel.getGuides(cityId = id).collectAsLazyPagingItems()}
                        else{viewModel.getGuides(categoryId = id).collectAsLazyPagingItems()},
                        moveToGuideDetail = moveToGuideDetail,
                        listState = listState,
                        isCollapsed = isCollapsed,
                        onBackClicked = onBackClicked,
                        modifier = modifier)
                }

            }
            is UiState.Failure ->{
                //TODO
            }
        }
    }
}

@Composable
fun CategoryContent(
    title: String,
    cityPic: String,
    guideData: LazyPagingItems<DataGuides>,
    moveToGuideDetail: (String) -> Unit,
    listState: LazyListState,
    isCollapsed: Boolean,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        CollapsedTopBar(modifier = modifier.zIndex(2f), title = title, isCollapsed = isCollapsed)

        LazyColumn(state = listState) {
            // TODO: Change data to arguments
            item { ExpandedToolbar(title = title, picture = cityPic, onBackClicked = onBackClicked) }
            items(guideData) { guide ->
                if (guide != null) {
                    UserComponent(
                        name = guide.name,
                        photoUrl = guide.picture,
                        place = guide.city,
                        specialization = guide.categories,
                        price = guide.pricePerDay,
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
            when(val state = guideData.loadState.refresh){
                is LoadState.Error -> {
                    //TODO
                }
                is LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp),
                                text = "Loading"
                            )

                            CircularProgressIndicator(color = Color.Black)
                        }
                    }
                }
                else -> {}
            }
            when(val state = guideData.loadState.append){
                is LoadState.Error -> {
                    //TODO
                }

                is LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(text = "Loading")

                            CircularProgressIndicator(color = Color.Black)
                        }
                    }
                }
                else -> {}

            }
        }
    }
}