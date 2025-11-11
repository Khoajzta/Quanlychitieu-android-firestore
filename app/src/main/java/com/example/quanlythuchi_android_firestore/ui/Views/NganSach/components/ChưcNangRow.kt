package com.example.quanlythuchi_android_firestore.ui.Views.NganSach.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.CustomButton
import com.example.quanlythuchi_android_firestore.ui.Views.home.components.CardFunction
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingMedium

@Composable
fun ChucNangRow(
    modifier: Modifier = Modifier,
    navController: NavController,
    userId: String,
){
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            CardFunction(
                title = "Chuyển tiền",
                gradientColors = listOf(
                    Color(0xFF4F7DF3),
                    Color(0xFF6BA9FF)
                ),
                iconPath = "file:///android_asset/icons/ic_chart.svg",
                onClick = {
                    navController.navigate(Screen.ChuyenTien.createRoute(userId))
                }
            )
        }

        item {
            CardFunction(
                title = "Lịch sử chuyển tiền",
                gradientColors = listOf(
                    Color(0xFFEFBA78),
                    Color(0xFFE5BD50)
                ),
                iconPath = "file:///android_asset/icons/ic_add_khoan_chi.svg",
                onClick = {
                    navController.navigate(Screen.LichSuChuyenTien.createRoute(userId))
                }
            )
        }

        item {
            CardFunction(
                title = "Tạo tài khoản",
                gradientColors = listOf(
                    Color(0xFF3AA4AD),
                    Color(0xFF23A47F)
                ),
                iconPath = "file:///android_asset/icons/ic_add_khoan_chi.svg",
                onClick = {
                    navController.navigate(Screen.AddTaiKhoan.createRoute(userId))
                }
            )
        }

    }
}

@Composable
fun FunctionRow(
    navController: NavController,
    userId: String
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PaddingMedium),
        horizontalArrangement = Arrangement.spacedBy(PaddingMedium),
    ) {
        item {
            CardFunction(
                title = "Thống kê trong năm",
                gradientColors = listOf(
                    Color(0xFF4F7DF3),
                    Color(0xFF6BA9FF)
                ),
                iconPath = "file:///android_asset/icons/ic_chart.svg",
                onClick = {
                    navController.navigate(Screen.ThongKeNam.createRoute(userId = userId))
                }
            )
        }

        item {
            CardFunction(
                title = "Thêm khoản chi",
                gradientColors = listOf(
                    Color(0xFF3AA4AD),
                    Color(0xFF23A47F)
                ),
                iconPath = "file:///android_asset/icons/ic_add_khoan_chi.svg",
                onClick = {
                    navController.navigate(Screen.AddKhoanChi.createRoute(userId = userId))
                }
            )
        }
    }
}