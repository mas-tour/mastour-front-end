package com.mastour.mastour.ui.screen.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GenderSelectionDialog(
    openDialog: MutableState<Boolean>,
    genderOptions: List<String>,
    selectedItem: MutableState<String>,
    onSubmitClicked: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Choose Gender")
            },
            text = {
                Column() {
                    genderOptions.forEach { gender ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedItem.value == gender,
                                    onClick = { selectedItem.value = gender },
                                    role = Role.RadioButton
                                )
                        ) {
                            RadioButton(
                                selected = selectedItem.value == gender,
                                onClick = null,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(gender, style = TextStyle(fontSize = 16.sp))
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onSubmitClicked
                ) {
                    Text("Confirm ")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Dismiss")
                }
            }
        )
    }
}
