package com.example.quanlythuchi_android_firestore.ui.Views.NganSach.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.CustomButton
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen

@Composable
fun ChucNangRow(
    modifier: Modifier = Modifier,
    navController: NavController,
    userId:Int,
){
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            CustomButton(
                title = "Chuyển tiền",
                onClick = {
                    navController.navigate(Screen.ChuyenTien.createRoute(userId))
                },
                icon = Icons.Default.SyncAlt
            )
        }
        item {
            CustomButton(
                title = "Lịch sử chuyển tiền",
                onClick = {
                    navController.navigate(Screen.LichSuChuyenTien.createRoute(userId))
                },
                icon = Icons.Default.History
            )
        }

        item {
            CustomButton(
                title = "Tạo tài khoản mới",
                onClick = {
                    navController.navigate(Screen.AddTaiKhoan.createRoute(userId))
                },
                icon = Icons.Default.Add
            )
        }
    }
}