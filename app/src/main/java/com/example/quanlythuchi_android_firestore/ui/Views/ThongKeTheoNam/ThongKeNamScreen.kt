package com.example.quanlythuchi_android_firestore.ui.Views.ThongKeTheoNam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quanlythuchi_android_firestore.Components.DotLoading
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeThangModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.ChiTieuViewModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.ThuNhapViewModel
import com.example.quanlythuchi_android_firestore.ui.Views.ThongKeTheoNam.components.BieuDoThongKe
import com.example.quanlythuchi_android_firestore.ui.Views.ThongKeTheoNam.components.CardThongKe
import com.example.quanlythuchi_android_firestore.ui.Views.ThongKeTheoNam.components.TopThongKe
import com.example.quanlythuchi_android_firestore.ui.components.Header
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.example.quanlythuchi_android_firestore.ui.theme.BackgroundColor
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceMedium
import java.time.LocalDate


@Composable
fun ThongKeTheoNamScreen(
    navController: NavController,
    userId: String,
    thuNhapViewModel: ThuNhapViewModel = hiltViewModel(),
    chiTieuViewModel: ChiTieuViewModel = hiltViewModel(),
) {
    val currentDate = LocalDate.now()
    val currentYear = currentDate.year

    val thongKeChiTieuState by chiTieuViewModel.thongKeState.collectAsState()
    val thongKeThuNhapState by thuNhapViewModel.thongKeTheoNamState.collectAsState()

    // Gọi API khi userId thay đổi
    LaunchedEffect(userId) {
        chiTieuViewModel.thongKeTheoNam(userId, currentYear)
        thuNhapViewModel.thongKeTheoNam(userId, currentYear)
    }

    // Kiểm tra state trước khi map
    val isLoading = thongKeChiTieuState is UiState.Loading || thongKeThuNhapState is UiState.Loading
    val isError = thongKeChiTieuState is UiState.Error || thongKeThuNhapState is UiState.Error
    val isSuccess = thongKeChiTieuState is UiState.Success && thongKeThuNhapState is UiState.Success

    val chitieuTheoNam = (thongKeChiTieuState as? UiState.Success)?.data ?: emptyList()
    val thuNhapTheoNam = (thongKeThuNhapState as? UiState.Success)?.data ?: emptyList()

    val thongKeList = (1..12).map { thang ->
        val chiThang = chitieuTheoNam.find { it.thang == thang }?.tongChi ?: 0L
        val thuThang = thuNhapTheoNam.find { it.thang == thang }?.tongThu ?: 0L

        ThongKeThangModel(
            thang = thang,
            tongChi = chiThang,
            tongThu = thuThang
        )
    }

    val tongChiNam = thongKeList.sumOf { it.tongChi }
    val tongThuNam = thongKeList.sumOf { it.tongThu }
    val chenhlech = tongThuNam - tongChiNam

    val topChi = thongKeList.sortedByDescending { it.tongChi }.take(3)
    val topThu = thongKeList.sortedByDescending { it.tongThu }.take(3)

    val maxValue = (thongKeList.maxOfOrNull { maxOf(it.tongChi, it.tongThu) } ?: 1).toFloat()
    val barWidth = 28.dp
    val barSpace = 24.dp
    val chartHeight = 400.dp

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            Header(
                navController = navController,
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = "Thống kê theo năm",
                userId = userId
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    DotLoading()
                }

                isError -> {
                    Text(
                        "Đã xảy ra lỗi khi tải dữ liệu",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }

                isSuccess -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = PaddingBody)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(SpaceMedium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            CardThongKe(
                                modifier = Modifier,
                                nam = currentYear,
                                tongChiNam = tongChiNam,
                                tongThuNam = tongThuNam,
                                chenhlech = chenhlech
                            )
                        }

                        item {
                            Text(
                                "Biểu đồ thống kê năm ${currentYear}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = Color.Black,
                            )
                        }

                        item {
                            BieuDoThongKe(
                                modifier = Modifier,
                                chartHeight = chartHeight,
                                barWidth = barWidth,
                                barSpace = barSpace,
                                thongKeList = thongKeList,
                                maxValue = maxValue
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(SpaceMedium))
                        }

                        item {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                LegendItem(color = Color(0xFF4ECDC4), text = "Thu nhập")
                                Spacer(Modifier.width(20.dp))
                                LegendItem(color = Color(0xFFFF6B6B), text = "Chi tiêu")
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.height(SpaceMedium))
                        }

                        item {
                            TopThongKe(
                                modifier = Modifier,
                                topChi = topChi,
                                topThu = topThu
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(30.dp))
                        }
                    }
                }

                else -> {
                    // Trường hợp chưa có dữ liệu (initial)
                    Text("Chưa có dữ liệu thống kê", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(20.dp)
                .background(color, CircleShape)
        )
        Spacer(Modifier.width(6.dp))
        Text(text, fontSize = 14.sp, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewThongKeTheoNamScreen() {
    val navController = rememberNavController()
    ThongKeTheoNamScreen(navController = navController , userId = "1")
}


