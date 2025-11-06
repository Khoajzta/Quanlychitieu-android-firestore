package com.example.quanlythuchi_android_firestore.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quanlythuchi_android_firestore.Components.ButtonBackToHome
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody

@Composable
fun Header(
    navController: NavController,
    modifier: Modifier = Modifier,
    title: String,
    userId: Int = 1
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = PaddingBody),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ButtonBack(navController, iconColor = Color.Black)

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = title,
            fontSize = 17.sp,
            color = Color.Black,
            modifier = Modifier.padding(vertical = 5.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        ButtonBackToHome(navController, userId)
    }
}


@Composable
@Preview()
fun TradeHeaderPreview(){
    var navController = rememberNavController()
    Header(navController, title = "Giao dịch", userId = 1)
}