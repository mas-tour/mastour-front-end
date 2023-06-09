package com.mastour.mastour.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mastour.mastour.data.remote.DataGuides
import com.mastour.mastour.ui.components.UserComponent
import com.mastour.mastour.ui.navigation.SearchTopBar
import com.mastour.mastour.ui.viewmodel.GuidesViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: GuidesViewModel = hiltViewModel(),
    moveToGuideDetail: (String) -> Unit,
)
{
    val query by viewModel.query

    SideEffect {
        viewModel.tryUserToken()
    }
    Scaffold(
        topBar = {
            SearchTopBar(search = query, onSearchTextChanged = viewModel::changeQuery)
        })
    {
        SearchContent(
            moveToGuideDetail = moveToGuideDetail,
            guides = viewModel.getGuides(query = query).collectAsLazyPagingItems(),
            contentPadding = it,
            modifier = modifier
        )
    }
}

@Composable
fun SearchContent(
    guides: LazyPagingItems<DataGuides>,
    moveToGuideDetail: (String) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.padding(contentPadding)){
        items(items = guides, key = {it.id}){guide->
            if (guide != null) {
                UserComponent(
                    name = guide.name,
                    photoUrl = guide.picture,
                    place = guide.city ,
                    specialization = guide.categories[0].name,
                    price = guide.pricePerDay,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(horizontal = 16.dp, vertical = 5.dp)
                        .shadow(elevation = 8.dp, ambientColor = MaterialTheme.colors.primary, spotColor = MaterialTheme.colors.primary).clickable {
                        moveToGuideDetail(guide.id)
                    }
                )
            }
        }
        when(val state = guides.loadState.refresh){
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
                            text = "Refresh Loading"
                        )

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {}
        }
        when(val state = guides.loadState.append){
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
                        Text(text = "Pagination Loading")

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }
            else -> {}

        }
    }
}
