package com.example.quanlythuchi_android_firestore.ui.Views.LichSuChuyenTien

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.DotLoading
import com.example.quanlythuchi_android_firestore.domain.model.ChuyenTienModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.ChuyenTienViewModel
import com.example.quanlythuchi_android_firestore.ui.components.CardLichSuChuyenTien
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.example.quanlythuchi_android_firestore.ui.theme.BackgroundColor
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceMedium
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import com.example.quanlythuchi_android_firestore.ui.components.Header


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LichSuChuyenTienScreen(
    navController: NavController,
    userId: String,
    chuyenTienViewModel: ChuyenTienViewModel = hiltViewModel()
) {
    val loadLichSuChuyenTienUIState by chuyenTienViewModel.uiState.collectAsState()

    LaunchedEffect(userId) {
        chuyenTienViewModel.getLichSuChuyenTienByUser(userId)
    }

    val isRefreshing = loadLichSuChuyenTienUIState is UiState.Loading
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            chuyenTienViewModel.getLichSuChuyenTienByUser(userId)
        }
    )

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            Header(
                navController = navController,
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = "Lịch sử chuyển tiền",
                userId = userId
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .pullRefresh(refreshState),
            contentAlignment = Alignment.Center
        ) {

            when (loadLichSuChuyenTienUIState) {

                is UiState.Loading -> {
                    DotLoading()
                }

                is UiState.Error -> {
                    Text(
                        text = "Lỗi tải dữ liệu, vui lòng thử lại!",
                        color = Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                is UiState.Success -> {
                    val listLichSuChuyenTien =
                        (loadLichSuChuyenTienUIState as UiState.Success<List<ChuyenTienModel>>).data

                    if (listLichSuChuyenTien.isEmpty()) {
                        Text(
                            text = "Chưa có lịch sử chuyển tiền",
                            color = Color.Gray,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = PaddingBody),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(SpaceMedium)
                        ) {
                            items(listLichSuChuyenTien) { chuyenTien ->
                                CardLichSuChuyenTien(chuyenTien = chuyenTien)
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = Color(0xFF1565C0)
            )
        }
    }
}
