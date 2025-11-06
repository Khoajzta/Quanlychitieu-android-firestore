package com.example.Quanlythuchi_android_firestore.ui.Views.AddKhoanChi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody

@Composable
fun ColorCircleItem(
    colorKey: String,
    isSelected: Boolean,
    onClick: (String) -> Unit
) {
    val gradient = when (colorKey) {
        "red" -> listOf(Color(0xFFE57373), Color(0xFFF06292).copy(alpha = 0.35f))
        "blue" -> listOf(Color(0xFF64B5F6), Color(0xFF4FC3F7).copy(alpha = 0.35f))
        "green" -> listOf(Color(0xFF81C784), Color(0xFF4DB6AC).copy(alpha = 0.35f))
        "yellow" -> listOf(Color(0xFFFFB74D), Color(0xFFFF8A65).copy(alpha = 0.35f))
        else -> listOf(Color.LightGray, Color.Gray)
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .size(50.dp)
            .background(
                brush = Brush.horizontalGradient(colors = gradient),
                shape = CircleShape
            )
            .clickable { onClick(colorKey) }
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = Color.White,
                shape = CircleShape
            )
    )
}

@Composable
fun ColorPickerRow(
    colorOptions: List<String>,
    selectedColor: String,
    onColorSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = PaddingBody)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        colorOptions.forEach { color ->
            ColorCircleItem(
                colorKey = color,
                isSelected = (selectedColor == color),
                onClick = onColorSelected
            )
        }
    }
}
