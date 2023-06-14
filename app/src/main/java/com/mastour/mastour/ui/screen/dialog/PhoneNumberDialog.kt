package com.mastour.mastour.ui.screen.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PhoneNumberDialog(
    openDialog: MutableState<Boolean>,
    phoneNumber: String,
    onPhoneNumberChanged: (String) -> Unit,
    onSubmitClicked: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Fill Phone Number")
            },
            text = {
                Column(Modifier.padding(top = 10.dp)) {
                    TextField(
                        value = phoneNumber,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        onValueChange = onPhoneNumberChanged,
                        singleLine = true
                    )
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