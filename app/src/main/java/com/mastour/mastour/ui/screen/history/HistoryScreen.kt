package com.mastour.mastour.ui.screen.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mastour.mastour.R
import com.mastour.mastour.data.remote.HistoryData
import com.mastour.mastour.ui.components.OrderComponent
import com.mastour.mastour.ui.screen.failureScreen.FailureScreen
import com.mastour.mastour.ui.viewmodel.GuidesViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: GuidesViewModel = hiltViewModel()
) {
    SideEffect {
        viewModel.tryUserToken()
    }

    HistoryContent(guides = viewModel.getHistory().collectAsLazyPagingItems(), modifier = modifier)
}

@Composable
fun HistoryContent(
    guides: LazyPagingItems<HistoryData>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.page_background1),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopCenter
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "History",
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(top = 60.dp, start = 16.dp)
                .align(Alignment.Start)
        )
        if (guides.itemCount > 0) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                items(items = guides, key = { it.id }) { guide ->
                    if (guide != null) {
                        OrderComponent(
                            guideName = guide.name,
                            location = guide.city,
                            status = guide.status,
                            price = guide.totalPrice,
                            duration = guide.countDay,
                            photoUrl = guide.picture
                        )
                    }
                }
                when (guides.loadState.refresh) {
                    is LoadState.Error -> {
                        item {
                            FailureScreen(
                                onRefreshClicked = { guides.refresh() },
                                modifier = modifier.fillMaxSize()
                            )
                        }
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
                when (guides.loadState.append) {
                    is LoadState.Error -> {
                        item {
                            FailureScreen(
                                onRefreshClicked = { guides.retry() },
                                modifier = modifier.fillMaxWidth()
                            )
                        }
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
        } else {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "This Page is Empty, Book First!",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}