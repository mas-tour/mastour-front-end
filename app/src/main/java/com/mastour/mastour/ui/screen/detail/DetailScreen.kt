package com.mastour.mastour.ui.screen.detail

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mastour.mastour.R
import com.mastour.mastour.data.remote.DataDetailGuides
import com.mastour.mastour.dummy.CategoryData2
import com.mastour.mastour.dummy.CategoryDatas2
import com.mastour.mastour.ui.components.CategoryComponent
import com.mastour.mastour.ui.components.CategoryComponent2
import com.mastour.mastour.ui.components.TagComponent
import com.mastour.mastour.ui.theme.MasTourTheme
import com.mastour.mastour.ui.viewmodel.GuidesViewModel
import com.mastour.mastour.util.UiState

@Composable
fun DetailScreen(
    id: String,
    onBackClicked: () -> Unit,
    onHireClicked: () -> Unit,
    onContactClicked: () -> Unit,
    viewModel: GuidesViewModel = hiltViewModel()
){
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
                        onContactClicked = onContactClicked,
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
    onContactClicked: () -> Unit,
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
                    text = "Specialization",
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
                    TagComponent(
                        name = dataDetailGuides.categories?.get(0)?.name.toString(),
                        color = MaterialTheme.colors.secondaryVariant
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
                OutlinedButton(onClick = onContactClicked, shape = RoundedCornerShape(16.dp), modifier = Modifier
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

//Just 4 Preview, To Be Deleted
@Composable
fun DetailContent2(
    modifier: Modifier = Modifier,
    name: String,
    age: Int,
    photoUrl: Int,
    place: String,
    price: Int,
    topData: List<CategoryData2>,
    description: String,
    specialization: String,
    onBackClicked: () -> Unit,
    onHireClicked: () -> Unit,
    onContactClicked: () -> Unit,
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
                    Image(
                        painter = painterResource(photoUrl),
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
                            Spacer(modifier = Modifier.size(20.dp))
                            Text(
                                text = "$name, $age",
                                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
                                color = MaterialTheme.colors.secondary
                            )
                            Spacer(modifier = Modifier.size(20.dp))
                        }
                    }
                }

                Text(
                    text = "Specialization",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(start = 16.dp, top = 32.dp)
                )

                Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                    TagComponent(
                        name = place,
                        color = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    TagComponent(
                        name = specialization,
                        color = MaterialTheme.colors.secondaryVariant
                    )
                }

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )

                Text(
                    text = description,
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
                    items(topData) { top ->
                        CategoryComponent2(
                            name = top.name,
                            photoUrl = top.photoUrl,
                            modifier = Modifier
                                .size(160.dp)
                                .padding(8.dp)
                        )
                    }
                }

            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)) {
                OutlinedButton(onClick = onContactClicked, shape = RoundedCornerShape(16.dp), modifier = Modifier
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
                            text = "Hire IDR $price",
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

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4, showSystemUi = true)
@Preview(showBackground = true, device = Devices.NEXUS_5X, showSystemUi = true)
fun DetailScreenPreview() {
    MasTourTheme {
        DetailContent2(
            name = "Jeremy Albertson",
            age = 32,
            photoUrl = R.drawable.dummy_user,
            place = "Kediri",
            specialization = "Historical",
            topData = CategoryDatas2.top,
            price = 200000,
            description = "Jerma has never spoken using his real voice. Fans theorize that his real voice was a " +
                    "supernatural weapon of some kind where upon hearing it, you are immediately transported to a realm of existence that " +
                    "is incomprehensible by the human mind. A realm where you are driven to insanity by immense, Lovecraftian horrors " +
                    "and an infinitely expanding pit of enveloping darkness.",
            onBackClicked = {},
            onHireClicked = {},
            onContactClicked = {},
        )

    }
}