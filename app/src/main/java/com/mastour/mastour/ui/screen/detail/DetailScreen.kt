package com.mastour.mastour.ui.screen.detail

import android.app.DatePickerDialog
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
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
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
import com.mastour.mastour.ui.screen.failureScreen.FailureScreen
import com.mastour.mastour.ui.screen.profile.advancedShadow
import com.mastour.mastour.ui.viewmodel.GuidesViewModel
import com.mastour.mastour.util.UiState
import com.mastour.mastour.util.formatNumber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.*

@Composable
fun DetailScreen(
    id: String,
    onBackClicked: () -> Unit,
    viewModel: GuidesViewModel = hiltViewModel()
) {
    SideEffect {
        viewModel.tryUserToken()
    }

    val context = LocalContext.current
    val popupControl = remember {
        mutableStateOf(false)
    }

    ConfirmHirePopup(popupControl = popupControl, onConfirmClicked = {
        viewModel.postBooking(id)
    })

    viewModel.bookGuideResponse.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                // Empty?
            }
            is UiState.Success -> {
                LaunchedEffect(key1 = true) {
                    Toast.makeText(context, "Guide booked succesfully!", Toast.LENGTH_SHORT).show()
                    popupControl.value = false
                }
            }
            is UiState.Failure -> {
                LaunchedEffect(key1 = true) {
                    Toast.makeText(context, "Failed to book guide", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    viewModel.detailResponse.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
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
            is UiState.Success -> {
                uiState.data?.data?.let {
                    DetailContent(
                        dataDetailGuides = it,
                        onBackClicked = onBackClicked,
                        onHireClicked = {
                            popupControl.value = true
                        },
                        context = context
                    )
                }
            }
            is UiState.Failure -> {
                FailureScreen(
                    onRefreshClicked = { viewModel.getDetailedGuide(id) },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun ConfirmHirePopup(
    popupControl: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    viewModel: GuidesViewModel = hiltViewModel(),
    onConfirmClicked: () -> Unit
) {
    val context = LocalContext.current

    var startDate by remember {
        mutableStateOf("Unset")
    }

    var endDate by remember {
        mutableStateOf("Unset")
    }

    val datePickerStart = DatePickerDialog(context)
    val datePickerEnd = DatePickerDialog(context)

    val startDateFilled = remember {
        mutableStateOf(false)
    }
    val endDateFilled = remember {
        mutableStateOf(false)
    }

    var timestampStart = 0L
    var timestampEnd = 0L

    datePickerStart.datePicker.minDate = Calendar.getInstance().timeInMillis
    datePickerStart.setOnDateSetListener { _, year, month, dayOfMonth ->
        val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
        val localDateTime = LocalDateTime.of(selectedDate, LocalTime.MIDNIGHT)
        startDate = "$dayOfMonth / $month / $year"
        timestampStart = localDateTime.toEpochSecond(ZoneOffset.UTC)
        timestampStart *= 1000
        viewModel.changeStart(timestampStart)
//        val instant = Instant.ofEpochSecond(timestampStart)
//        val nextDayInstant = instant.plusSeconds(86400)
        datePickerEnd.datePicker.minDate = timestampStart
        startDateFilled.value = true
        endDateFilled.value = timestampEnd - timestampStart > 0
    }

    datePickerEnd.setOnDateSetListener { _, year, month, dayOfMonth ->
        val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
        val localDateTime = LocalDateTime.of(selectedDate, LocalTime.MIDNIGHT)
        endDate = "$dayOfMonth / $month / $year"
        timestampEnd = localDateTime.toEpochSecond(ZoneOffset.UTC)
        timestampEnd *= 1000
        viewModel.changeEnd(timestampEnd)
        endDateFilled.value = timestampEnd - timestampStart > 0
    }

    if (popupControl.value) {
        AlertDialog(
            onDismissRequest = {
                popupControl.value = false
            },
            title = {
                Text(
                    text = "Booking Menu",
                    style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                    modifier = modifier.padding(8.dp)
                )
            },
            text = {
                Box(
                    modifier = modifier
                        .fillMaxWidth(0.9F)
                        .background(Color.White)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Choose Tour Start Date!",
                            modifier = modifier.align(Alignment.Start)
                        )
                        Button(
                            onClick = { datePickerStart.show() },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            elevation = ButtonDefaults.elevation(0.dp),
                            shape = RoundedCornerShape(30.dp),
                            contentPadding = PaddingValues(10.dp),
                            modifier = modifier
                                .fillMaxWidth()
                                .advancedShadow(
                                    shadowRadius = 3.dp,
                                    borderRadius = 30.dp,
                                    offsetY = 3.dp
                                )

                        )
                        {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = modifier.padding(horizontal = 15.dp)
                            ) {
                                Icon(Icons.Filled.CalendarToday, contentDescription = "Age")
                                Spacer(modifier.width(14.dp))
                                Text(text = startDate, style = MaterialTheme.typography.subtitle2)
                                Spacer(modifier.weight(1f))
                            }
                        }

                        Text(
                            text = "Choose Tour End Date!",
                            modifier = modifier.align(Alignment.Start)
                        )
                        Button(
                            onClick = { datePickerEnd.show() },
                            enabled = startDateFilled.value,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            elevation = ButtonDefaults.elevation(0.dp),
                            shape = RoundedCornerShape(30.dp),
                            contentPadding = PaddingValues(10.dp),
                            modifier = modifier
                                .fillMaxWidth()
                                .advancedShadow(
                                    shadowRadius = 3.dp,
                                    borderRadius = 30.dp,
                                    offsetY = 3.dp
                                )
                        )
                        {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = modifier.padding(horizontal = 15.dp)
                            ) {
                                Icon(Icons.Filled.CalendarToday, contentDescription = "Age")
                                Spacer(modifier.width(14.dp))
                                Text(text = endDate, style = MaterialTheme.typography.subtitle2)
                                Spacer(modifier.weight(1f))
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onConfirmClicked,
                    enabled = endDateFilled.value
                ) {
                    Text("Confirm Hire")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        popupControl.value = false
                    }) {
                    Text("Dismiss")
                }
            }
        )
    }
}

@Composable
fun DetailContent(
    dataDetailGuides: DataDetailGuides,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onHireClicked: () -> Unit,
    context: Context,
) {
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

                LazyRow(modifier = Modifier
                    .padding(end = 8.dp, top = 8.dp, start = 16.dp)){
                    items(items = dataDetailGuides.categories, key = { it.id }) {categories ->
                        TagComponent(
                            name = categories.name,
                            color = MaterialTheme.colors.secondaryVariant,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
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
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
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
                        .width(this@BoxWithConstraints.maxWidth / 2)
                ) {
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
                            text = "Hire IDR " + dataDetailGuides.pricePerDay?.let { formatNumber(it) },
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

