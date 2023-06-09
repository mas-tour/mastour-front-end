package com.mastour.mastour.ui.screen.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mastour.mastour.data.remote.DataDetailGuides
import com.mastour.mastour.ui.components.CategoryComponent
import com.mastour.mastour.ui.components.TagComponent
import com.mastour.mastour.ui.viewmodel.GuidesViewModel
import com.mastour.mastour.util.UiState

@Composable
fun DetailScreen(
    id: String,
    onBackClicked: () -> Unit,
    onHireClicked: () -> Unit,
    viewModel: GuidesViewModel = hiltViewModel()
){

    val context = LocalContext.current
    viewModel.detailResponse.collectAsState(UiState.Loading).value.let {uiState->
        when(uiState){
            is UiState.Loading ->{
                viewModel.getDetailedGuide(id)
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
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
            is UiState.Success ->{
                uiState.data?.data?.let {
                    DetailContent(
                        dataDetailGuides = it,
                        onBackClicked = onBackClicked,
                        onHireClicked = onHireClicked,
                        context = context
                    )
                }
            }
            is UiState.Failure ->{
                //TODO
            }
        }
    }
}

@Composable
fun DetailContent(
    dataDetailGuides: DataDetailGuides,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onHireClicked: () -> Unit,
    context: Context,
){
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((this@BoxWithConstraints.maxHeight - 80.dp))
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    AsyncImage(
                        model = dataDetailGuides.detailPicture,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                shape = RoundedCornerShape(
                                    bottomEnd = 8.dp,
                                    bottomStart = 8.dp,
                                    topEnd = 0.dp,
                                    topStart = 0.dp
                                )
                            ),

                        )

                    IconButton(
                        onClick = onBackClicked,
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.shadow(elevation = 8.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .offset(x = 8.dp, y = 16.dp)
                            .shadow(elevation = 20.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(color = Color.White)
                    ) {
                        Row(modifier = Modifier.padding(8.dp)) {
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = "${dataDetailGuides.name}, ${dataDetailGuides.gender}",
                                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
                                color = MaterialTheme.colors.secondary
                            )
                            Spacer(modifier = Modifier.size(20.dp))
                        }
                    }
                }

                Text(
                    text = "Cities",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(start = 16.dp, top = 32.dp)
                )

                Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                    TagComponent(
                        name = dataDetailGuides.city.toString(),
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
                Text(
                    text = "Specialization",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )

                Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                    TagComponent(
                        name = dataDetailGuides.categories?.get(0)?.name.toString(),
                        color = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    TagComponent(
                        name = dataDetailGuides.categories?.get(1)?.name.toString(),
                        color = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    TagComponent(
                        name = dataDetailGuides.categories?.get(2)?.name.toString(),
                        color = MaterialTheme.colors.secondaryVariant,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )

                Text(
                    text = dataDetailGuides.description.toString(),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                    textAlign = TextAlign.Justify,
                )

                Text(
                    text = "Top 3 Places",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )

                LazyRow(modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)) {
                    items(dataDetailGuides.topPlaces) { top ->
                        top.picture?.let {
                            CategoryComponent(
                                name = top.name.toString(),
                                photoUrl = it,
                                modifier = Modifier
                                    .size(160.dp)
                                    .padding(8.dp)
                            )
                        }
                    }
                }

            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)) {
                OutlinedButton(
                    onClick = {
                        val u = Uri.parse("tel:${dataDetailGuides.phoneNumber}")
                        val i = Intent(Intent.ACTION_DIAL, u)
                        try {
                            context.startActivity(i)
                        } catch (s: SecurityException) {
                            Toast.makeText(context, "An error occurred", Toast.LENGTH_LONG)
                                .show()
                        }
                    },
                    shape = RoundedCornerShape(16.dp), modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, end = 8.dp, start = 8.dp)
                    .height(48.dp)
                    .width(this@BoxWithConstraints.maxWidth / 2)) {
                    Text(
                        text = "Contact",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                        color = MaterialTheme.colors.primary,
                    )
                }
                Button(
                    onClick = onHireClicked,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp, end = 8.dp)
                        .height(48.dp)
                        .width(this@BoxWithConstraints.maxWidth / 2),
                    contentPadding = PaddingValues()

                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colors.primary,
                                        MaterialTheme.colors.secondary
                                    )
                                )
                            )
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Hire IDR ${dataDetailGuides.pricePerDay}",
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    }

                }
            }
        }
    }
}

