package com.example.quanlythuchi_android_firestore.ui.Views.AddKhoanChi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quanlythuchi_android_firestore.ui.components.CustomCircleButton
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusFull


@Composable
fun EmojiItem(
    emoji: String,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(RadiusFull))
            .background(color = Color.White)
            .clickable { onClick(emoji) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize
        )
    }
}


@Composable
fun EmojiRow(
    listEmoji: List<String>,
    onClickEmoji: (String) -> Unit,
    onClickMore: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(horizontal = PaddingBody)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        listEmoji.forEach { emoji ->
            EmojiItem(

                emoji = emoji,
                onClick = onClickEmoji
            )
        }

        CustomCircleButton(
            modifier = Modifier.size(50.dp),
            onClick = onClickMore,
            icon = Icons.Default.MoreHoriz,
            iconTint = Color.Gray
        )
    }
}
