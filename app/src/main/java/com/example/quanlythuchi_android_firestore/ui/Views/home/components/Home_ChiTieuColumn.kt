package com.example.quanlythuchi_android_firestore.ui.Views.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.CardThuNhap
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.ui.components.CardChiTieu
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.PrimaryColor

@Composable
fun HomeChiTieuColumn(
    navController: NavController,
    userId:Int,
    listChiTieu :List<ChiTieuModel>
){
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Chi tiêu gần đây",
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                fontSize = 16.sp,
            )

            TextButton(
                modifier = Modifier,
                onClick = {
                    navController.navigate(Screen.ListChiTieuTheoThang.createRoute(userId))
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

        Column(
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            for(item in listChiTieu){
                CardChiTieu(chitieu = item)
            }
        }
    }

}