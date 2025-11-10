package com.example.quanlythuchi_android_firestore.ui.Views.Trade

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.CustomButton
import com.example.quanlythuchi_android_firestore.Components.DotLoading
import com.example.quanlythuchi_android_firestore.ViewModels.KhoanChiViewModel
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.ui.Views.Trade.Components.TradeTabPage
import com.example.quanlythuchi_android_firestore.ui.components.Header
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.example.quanlythuchi_android_firestore.ui.theme.BackgroundColor
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import kotlinx.coroutines.delay
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun TradeScreen(
    navController: NavController,
    userId: String,
    khoanChiViewModel: KhoanChiViewModel = hiltViewModel(),
) {
    val khoanChiUiState by khoanChiViewModel.loadtheothang.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }

    val currentDate = LocalDate.now()
    val currentMonth = currentDate.monthValue
    val currentYear = currentDate.year

    // ✅ Gọi API khi mở màn hình
    LaunchedEffect(userId) {
        khoanChiViewModel.getKhoanChiTheThangVaNam(userId.toString(), currentMonth, currentYear)
    }

    // ✅ Cập nhật tự động mỗi 15 phút (không block UI)
    LaunchedEffect(userId) {
        khoanChiViewModel.getAllKhoanChiByUser(userId.toString())
    }

    // ✅ Trạng thái refresh
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            khoanChiViewModel.getAllKhoanChiByUser(userId.toString())
            isRefreshing = false
        }
    )

    // ✅ Lấy danh sách từ state
    val khoanChiList = when (khoanChiUiState) {
        is UiState.Success -> (khoanChiUiState as UiState.Success<List<KhoanChiModel>>).data
        else -> emptyList()
    }

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            Header(
                navController = navController,
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = "Giao dịch",
                userId = userId
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pullRefresh(pullRefreshState)
        ) {
            when (val state = khoanChiUiState) {
                is UiState.Loading -> {
                    DotLoading(modifier = Modifier.align(Alignment.Center))
                }

                is UiState.Success -> {
                    if (khoanChiList.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Chưa có giao dịch nào",
                                color = Color.Gray,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    } else {
                        TradeTabPage(
                            navController = navController,
                            listKhoanChi = khoanChiList,
                            userId = userId
                        )
                    }
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            color = Color.Gray,
                            fontSize = 16.sp
                        )
                    }
                }

                else -> Unit
            }

            // ✅ Nút thêm giao dịch
            CustomButton(
                modifier = Modifier
                    .padding(horizontal = PaddingBody)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = PaddingBody),
                title = "Thêm giao dịch",
                onClick = {
                    navController.navigate(Screen.AddTrade.createRoute(userId))
                },
                icon = Icons.Default.AddCircle
            )

            // ✅ Hiển thị vòng tròn refresh ở trên cùng
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}


@Composable
@Preview
fun TradeScreenPreview() {
//    var navController = rememberNavController()
//    TradeScreen(navController)
}


