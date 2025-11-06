package com.example.quanquanlythuchi_android_firestorelychitieu.ui.Views.Profile.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileNameEmail(
    name: String,
    email: String
){
    Text(
        text = name,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(4.dp))

// Email
    Text(
        text = email,
        fontSize = 16.sp,
        color = Color.Gray,
        textAlign = TextAlign.Center
    )
}