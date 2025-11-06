package com.example.quanlythuchi_android_firestore.ui.Views.Profile.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun ProfileAvartar(
    modifier: Modifier = Modifier,
    url: String,
){
    AsyncImage(
        model = url,
        contentDescription = "Avatar người dùng",
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(
                3.dp,
                Color(0xFF2196F3),
                CircleShape
            )
            .shadow(4.dp, CircleShape),
        contentScale = ContentScale.Crop
    )
}