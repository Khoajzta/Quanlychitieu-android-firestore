package com.example.quanlythuchi_android_firestore.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun ThongBaoDialog(
    title: String,
    message: String,
    confirmText: String = "Đồng ý",
    dismissText: String = "Hủy",
    confirmButtonColor: Color = Color.Red,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        title = { Text(text = title, color = Color.Black) },
        text = { Text(text = message, color = Color.Black) },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = confirmButtonColor),
                onClick = onConfirm
            ) {
                Text(confirmText, color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(dismissText, color = Color.Black)
            }
        }
    )
}