package com.example.quanlythuchi_android_firestore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class SnackbarType {
    SUCCESS,
    ERROR,
    INFO
}
@Composable
fun CustomSnackbar(
    message: String,
    type: SnackbarType,
    modifier: Modifier = Modifier
) {
    val gradientBrush = when (type) {
        SnackbarType.SUCCESS -> Brush.linearGradient(
            colors = listOf(Color(0xFF4CAF50), Color(0xFF2E7D32)) // xanh lá gradient
        )
        SnackbarType.ERROR -> Brush.linearGradient(
            colors = listOf(Color(0xFFF44336), Color(0xFFD32F2F)) // đỏ gradient
        )
        SnackbarType.INFO -> Brush.linearGradient(
            colors = listOf(Color(0xFF2196F3), Color(0xFF1976D2)) // xanh dương gradient
        )
    }

    val icon = when (type) {
        SnackbarType.SUCCESS -> Icons.Default.CheckCircle
        SnackbarType.ERROR -> Icons.Default.Error
        SnackbarType.INFO -> Icons.Default.Info
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(brush = gradientBrush, shape = RoundedCornerShape(14.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = message,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}


@Composable
@Preview
fun CustomSnackBarPreview(){

    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarType by remember { mutableStateOf(SnackbarType.INFO) }
    var snackbarMessage by remember { mutableStateOf("") }

    CustomSnackbar(
        message = snackbarMessage,
        type = snackbarType
    )
}
