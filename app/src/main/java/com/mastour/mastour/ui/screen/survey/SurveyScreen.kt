package com.mastour.mastour.ui.screen.survey

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mastour.mastour.R
import com.mastour.mastour.data.local.Question
import com.mastour.mastour.data.local.QuestionsData
import com.mastour.mastour.ui.components.QuestionComponent
import com.mastour.mastour.ui.navigation.Screen
import com.mastour.mastour.ui.screen.register.RegisterContent
import com.mastour.mastour.ui.theme.MasTourTheme
import com.mastour.mastour.ui.viewmodel.SurveyViewModel
import com.mastour.mastour.util.UiState

@Composable
fun SurveyScreen(
    viewModel: SurveyViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val currentPageIndex = remember {
        mutableStateOf(0)
    }
    val startIndex = currentPageIndex.value * 5
    val endIndex = minOf(startIndex + 5, QuestionsData.questions.size)
    val visibleQuestions = QuestionsData.questions.subList(startIndex, endIndex)

    val answers: List<MutableState<Int>> = List(5) {
        mutableStateOf(0)
    }

    viewModel.surveyResponse.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                SurveyContent(
                    page = currentPageIndex,
                    onNextClicked = {
                        currentPageIndex.value++
                        viewModel.addAnswers(answers)
                    },
                    onSubmitClicked = {
                        viewModel.addAnswers(answers)
                        viewModel.postAnswers()
                    },
                    surveyData = visibleQuestions,
                    answer = answers
                )
            }
            is UiState.Success -> {
                // TODO: Navigate to Matchmaking Results
                navHostController.navigate(Screen.Home.route){
                    popUpTo(navHostController.graph.findStartDestination().id){
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
            }
            is UiState.Failure -> {
                // TODO: Toast o dialogue, Register failed
            }
        }
    }
}

@Composable
fun SurveyContent(
    modifier: Modifier = Modifier,
    page: MutableState<Int>,
    answer: List<MutableState<Int>>,
    onNextClicked: () -> Unit,
    onSubmitClicked: () -> Unit,
    surveyData: List<Question>
) {
    Box(
        modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(R.drawable.wayang_background),
                contentScale = ContentScale.FillBounds,
                alpha = .15F
            )
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(11.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState()) // TODO: Make it scroll to 0 after every next
    )
    {
        // TODO: [NOTE] Don't know how to acquire data when using LazyColumn, using the scuffed solutions for now
//        LazyColumn(verticalArrangement = Arrangement.spacedBy(11.dp)) {
//            items(surveyData) { survey ->
//                QuestionComponent(question = survey.question, selectedValue = selectedValue)
//            }
//        }
        QuestionComponent(question = surveyData[0].question, selectedValue = answer[0])
        QuestionComponent(question = surveyData[1].question, selectedValue = answer[1])
        QuestionComponent(question = surveyData[2].question, selectedValue = answer[2])
        QuestionComponent(question = surveyData[3].question, selectedValue = answer[3])
        QuestionComponent(question = surveyData[4].question, selectedValue = answer[4])

        Spacer(modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .height(56.dp)
                .padding(bottom = 20.dp, start = 12.dp, end = 5.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxHeight()) {
                Text(
                    text = "${page.value + 1} / 5",
                    color = Color.Gray,
                    modifier = modifier.padding(top = 2.dp)
                )
            }
            Spacer(modifier.weight(1f))
            Button(
                onClick =
                if (page.value == 4) {
                    onSubmitClicked
                } else {
                    onNextClicked
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight(),
                contentPadding = PaddingValues()

            ){
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
                ){
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = if (page.value == 4) {
                            "Submit"
                        } else {
                            "Next  "
                        },
                            color = Color.White
                        )
                        Icon(
                            Icons.Filled.ArrowForward,
                            contentDescription = "Next Button",
                            tint = Color.White,
                            modifier = modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true, device = Devices.PIXEL_4)
//@Composable
//fun SurveyScreenPreview() {
//    MasTourTheme {
//        SurveyContent(surveyData = QuestionData.questions, page = 1, onNextClicked = {})
//    }
//}
