package com.example.newstudysmart.presentation.components
//ctrl + ALt + O remove the unwanted import
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteDialogBox(
    isOpen : Boolean,
    title : String = "Add/Update Subject",
    bodyText: String ,
    onDismissRequest: ()-> Unit,
    onConfirmButtonClick: () -> Unit
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = title) },
            text = { Text(text = bodyText) },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {

                TextButton(
                    onClick = onConfirmButtonClick ,

                ) {
                    Text(text = "Delete")
                }

            }
        )
    }
}