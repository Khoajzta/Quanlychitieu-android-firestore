package com.example.quanlythuchi_android_firestore.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusLarge

@Composable
fun CustomFAB(
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .shadow(5.dp, shape = RoundedCornerShape(RadiusLarge))
            .background(Color(0xFF2196F3), RoundedCornerShape(RadiusLarge))
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            Spacer(Modifier.width(8.dp))
            Text("Thêm khoản chi", color = Color.White)
        }
    }
}