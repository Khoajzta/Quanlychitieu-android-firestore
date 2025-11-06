package com.example.quanlythuchi_android_firestore.ui.Views.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.PrimaryColor

@Composable
fun KhoanChiMoreRow(
    modifier: Modifier = Modifier,
    navController: NavController,
    userId :Int
){
    Row(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Các khoản hay chi tiêu",
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            fontSize = 16.sp
        )

        TextButton(
            onClick = {
                navController.navigate(Screen.ListKhoanChi.createRoute(userId))
            }
        ) {
            Text(
                text = "Xem tất cả",
                fontWeight = FontWeight.Bold,
                color = PrimaryColor,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
@Preview
fun KhoanChiMoreRowPreview(){
    var navController = rememberNavController()
    KhoanChiMoreRow(modifier = Modifier, navController=navController, 1)
}