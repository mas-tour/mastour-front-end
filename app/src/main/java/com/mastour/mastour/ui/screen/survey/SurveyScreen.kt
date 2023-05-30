package com.mastour.mastour.ui.screen.survey

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
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
import com.mastour.mastour.R
import com.mastour.mastour.dummy.Question
import com.mastour.mastour.dummy.QuestionData
import com.mastour.mastour.ui.components.QuestionComponent
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun SurveyScreen() {

}

@Composable
fun SurveyContent(
    modifier: Modifier = Modifier,
    page: Int,
    onNextClicked: () -> Unit,
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
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
            .padding(top = 10.dp)
    )
    {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(11.dp)) {
            items(surveyData) { survey ->
                QuestionComponent(question = survey.question)
            }
        }
        Spacer(modifier.weight(1f))
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .height(56.dp)
                .padding(bottom = 20.dp, start = 12.dp, end = 5.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxHeight()) {
                Text(
                    text = "$page / 5",
                    color = Color.Gray,
                    modifier = modifier.padding(top = 2.dp)
                )
            }
            Spacer(modifier.weight(1f))
            Button(
                onClick = onNextClicked,
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
                        Text(text = "Next", color = Color.White)
                        Icon(
                            Icons.Filled.ArrowForward,
                            contentDescription = if (page != 5) {
                                "Next  "
                            } else {
                                "Submit"
                            },
                            tint = Color.White,
                            modifier = modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun SurveyScreenPreview() {
    MasTourTheme {
        SurveyContent(surveyData = QuestionData.questions, page = 1, onNextClicked = {})
    }
}
