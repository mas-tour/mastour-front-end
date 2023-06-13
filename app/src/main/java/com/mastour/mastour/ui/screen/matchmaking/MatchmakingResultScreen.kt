package com.mastour.mastour.ui.screen.matchmaking

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mastour.mastour.R
import com.mastour.mastour.data.remote.SurveyResults
import com.mastour.mastour.ui.components.ExtendedUserComponent
import com.mastour.mastour.ui.viewmodel.MatchViewModel
import com.mastour.mastour.util.UiState
import com.mastour.mastour.util.booleanToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MatchmakingResultsScreen(
    modifier: Modifier = Modifier,
    viewModel: MatchViewModel = hiltViewModel(),
    moveToGuideDetail: (String) -> Unit,
) {
    SideEffect {
        viewModel.tryUserToken()
    }
    val context = LocalContext.current

    viewModel.surveyResultsResponse.collectAsState(initial = UiState.Loading).value.let { uiState->
        when(uiState){
            is UiState.Loading -> {
                viewModel.getSurveyResults()
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
            is UiState.Success -> {
                uiState.data?.let { MatchmakingResultContent(userList = it.data, moveToGuideDetail = moveToGuideDetail ) }
            }
            is UiState.Failure -> {
                viewModel.categoriesResponse.collectAsState(initial = UiState.Loading).value.let {secondState ->
                    when(secondState){
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
                            var expanded by remember { mutableStateOf(false) }
                            var selectedText by remember {
                                mutableStateOf(secondState.data?.citiesResponse?.data?.get(0)?.name.toString())
                            }
                            LaunchedEffect(key1 = true) {
                                secondState.data?.citiesResponse?.data?.get(0).let {
                                    if (it != null) {
                                        viewModel.changeIdCity(
                                            it.id)
                                    }
                                }
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text(
                                    text = "Pick the city that you want to go to",
                                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
                                    color = MaterialTheme.colors.secondary,
                                    modifier = Modifier.padding(start = 16.dp, top = 32.dp)
                                )
                                ExposedDropdownMenuBox(
                                    expanded = expanded,
                                    onExpandedChange = { expanded = !expanded }) {
                                    TextField(
                                        colors = TextFieldDefaults.textFieldColors(
                                            backgroundColor = Color.White,
                                            leadingIconColor = MaterialTheme.colors.primary,
                                            focusedIndicatorColor = MaterialTheme.colors.secondary,
                                            unfocusedIndicatorColor = MaterialTheme.colors.secondary,
                                            placeholderColor = Color.Gray,
                                            textColor = Color.Black,
                                        ),
                                        value = selectedText,
                                        onValueChange = {},
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expanded
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(top = 16.dp)
                                    )
                                    ExposedDropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false }) {
                                        secondState.data?.citiesResponse?.data?.forEach{ city ->
                                            DropdownMenuItem(
                                                onClick = {
                                                    selectedText = city.name
                                                    viewModel.changeIdCity(city.id)
                                                    expanded = false
                                                    Toast.makeText(context, "${viewModel.idCity}", Toast.LENGTH_SHORT).show()

                                                },
                                            ) {
                                                Text(text = city.name)
                                            }
                                        }
                                    }
                                }
                                Text(
                                    text = "Pick the categories",
                                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.ExtraBold),
                                    color = MaterialTheme.colors.secondary,
                                    modifier = Modifier.padding(start = 16.dp, top = 32.dp)
                                )
                                secondState.data?.specResponse?.data?.forEach { spec ->
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        var checked by remember {
                                            mutableStateOf(false)
                                        }
                                        Checkbox(checked = checked, onCheckedChange = {
                                            checked = it

                                            when (spec.name) {
                                                "Historical" -> {
                                                    viewModel.todoListState[0] = booleanToInt(it)
                                                }
                                                "Adventure" -> {
                                                    viewModel.todoListState[1] = booleanToInt(it)
                                                }
                                                "Nature and Wildlife" -> {
                                                    viewModel.todoListState[2] = booleanToInt(it)
                                                }
                                                "Culinary" -> {
                                                    viewModel.todoListState[3] = booleanToInt(it)
                                                }
                                                "Wellness and Retreat" -> {
                                                    viewModel.todoListState[4] = booleanToInt(it)
                                                }
                                                "Architectural" -> {
                                                    viewModel.todoListState[5] = booleanToInt(it)
                                                }
                                                "Educational" -> {
                                                    viewModel.todoListState[6] = booleanToInt(it)
                                                }
                                                "Shopping" -> {
                                                    viewModel.todoListState[7] = booleanToInt(it)
                                                }
                                            }
                                        })
                                        Text(
                                            text = spec.name,
                                            style = MaterialTheme.typography.subtitle2.copy(
                                                fontWeight = FontWeight.Normal,
                                                color = MaterialTheme.colors.primary,
                                            ),
                                        )
                                    }
                                }
                                Button(
                                    onClick = {
                                        viewModel.getSurveyResults()
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                                    modifier = Modifier
                                        .padding(top = 16.dp)
                                        .width(120.dp)
                                        .height(48.dp),
                                    contentPadding = PaddingValues(),
                                    enabled = !viewModel.todoListState.all {
                                        it == 0
                                    }

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
                                            text = if(!viewModel.todoListState.all { it == 0 }){ "Submit" }else{"Choose 1"},
                                            color = Color.White)
                                    }

                                }
                                Text(
                                    text = if (!viewModel.todoListState.all { it == 0 }) {
                                        ""
                                    } else {
                                        "You must choose at least one category!"
                                    },
                                    color = MaterialTheme.colors.error,
                                    style = MaterialTheme.typography.subtitle2.copy(
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colors.primary,
                                    ),
                                    modifier = Modifier.padding(4.dp)
                                )

                            }

                        }
                        is UiState.Failure ->{
                            LaunchedEffect(key1 = true) {
                                Toast.makeText(context, "${uiState.e?.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MatchmakingResultContent(
    modifier: Modifier = Modifier,
    userList : List<SurveyResults>,
    moveToGuideDetail: (String) -> Unit,
){
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
            text = "Your Results",
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(top = 60.dp, start = 16.dp)
                .align(Alignment.Start)
        )
        LazyColumn(
            modifier = modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            items(items = userList, key = {it.id}){guides->
                ExtendedUserComponent(
                    name = guides.name,
                    photoUrl = guides.picture,
                    place = guides.city,
                    specialization = guides.categories,
                    price = guides.pricePerDay.toLong(),
                    desc = guides.description,
                    percentage = guides.percentage,
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .height(220.dp)
                        .clickable {
                            moveToGuideDetail(guides.id)
                        }
                )
            }
        }
    }
}