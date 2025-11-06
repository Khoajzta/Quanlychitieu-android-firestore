package com.example.quanlythuchi_android_firestore.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusFull

@Composable
fun SearchRow() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .weight(5f)

                .shadow(12.dp, RoundedCornerShape(RadiusFull))
                .clip(RoundedCornerShape(RadiusFull)),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            placeholder = { Text(text = "Search" , color = Color.Gray) },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xFFF0F0F0),
                unfocusedContainerColor = Color(0xFFF0F0F0),
                disabledContainerColor = Color(0xFFF0F0F0)
            )
        )

//        Spacer(modifier = Modifier.width(20.dp))
//
//        Box(
//            modifier = Modifier
//                .size(50.dp) // Kích thước vuông để tròn đều
//                .shadow(12.dp, RoundedCornerShape(50.dp))
//                .clip(RoundedCornerShape(50.dp))
//                .background(Color(0xFF268EBE)),
//            contentAlignment = Alignment.Center
//        ) {
//            IconButton(
//                onClick = { /* Handle filter click */ },
//                modifier = Modifier.size(40.dp) // Giữ icon nhỏ gọn bên trong
//            ) {
//                Icon(
//                    imageVector = Icons.Default.FilterAlt,
//                    contentDescription = null,
//                    tint = Color.White
//                )
//            }
//        }

    }
}

@Composable
@Preview
fun SearchRowPreview() {
    SearchRow()
}