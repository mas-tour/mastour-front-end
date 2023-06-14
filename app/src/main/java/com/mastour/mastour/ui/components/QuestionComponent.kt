package com.mastour.mastour.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mastour.mastour.ui.theme.MasTourTheme

@Composable
fun QuestionComponent(
    modifier: Modifier = Modifier,
    question: String,
    selectedValue: MutableState<Int>
) {
    val items = listOf(1, 2, 3, 4, 5)

    val isSelectedItem: (Int) -> Boolean = { selectedValue.value == it }
    val onChangeState: (Int) -> Unit = { selectedValue.value = it }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier.padding(vertical = 20.dp, horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                question,
                style = MaterialTheme.typography.subtitle1
                    .copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    ),
                textAlign = TextAlign.Center,
                modifier = modifier.padding(bottom = 10.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {

                items.forEach { item ->
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = modifier.height(40.dp)
                    ) {
                        val number = item
                        Text(
                            text = number.toString(),
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.Gray
                            ),
                            modifier = modifier
                                .align(Alignment.CenterHorizontally)
                        )
                        RadioButton(
                            selected = isSelectedItem(item),
                            onClick = { onChangeState(item) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colors.primary
                            )
                        )
                    }

                }
            }
            Row(modifier.padding(top = 10.dp)) {
                Text(
                    "Disagree",
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal)
                )
                Spacer(modifier.weight(1f))
                Text(
                    "Agree",
                    style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewQuestionComponent() {
    MasTourTheme {
        QuestionComponent(question = "Apakah anda suka terbang?", selectedValue = remember {
            mutableStateOf(3)
        })
    }
}