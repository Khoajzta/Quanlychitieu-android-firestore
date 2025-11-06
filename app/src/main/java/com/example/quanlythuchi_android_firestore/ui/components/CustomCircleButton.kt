package com.example.quanlythuchi_android_firestore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    icon: ImageVector,
    color: Color = Color.White,
    iconTint: Color = Color.Black
) {
    Box(
        modifier = modifier
            .size(48.dp) // Kích thước nút tròn
            .background(color, shape = CircleShape)
            .clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
@Preview
fun CustomCircleButtonPreview(){
    CustomCircleButton(
        modifier = Modifier,
        onClick = {},
        icon = Icons.Default.MoreHoriz
    )
}