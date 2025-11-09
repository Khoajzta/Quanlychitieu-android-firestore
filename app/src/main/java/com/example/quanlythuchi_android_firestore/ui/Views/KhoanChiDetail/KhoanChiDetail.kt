package com.example.quanlythuchi_android_firestore.ui.Views.KhoanChiDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.quanlythuchi_android_firestore.Components.DotLoading
import com.example.quanlythuchi_android_firestore.ui.ViewModels.ChiTieuViewModel
import com.example.quanlythuchi_android_firestore.ui.components.CardChiTieuSwipeToDelete
import com.example.quanlythuchi_android_firestore.ui.components.CustomSnackbar
import com.example.quanlythuchi_android_firestore.ui.components.Header
import com.example.quanlythuchi_android_firestore.ui.components.SnackbarType
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.example.quanlythuchi_android_firestore.ui.theme.BackgroundColor
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun KhoanChiDetailScreen(
    navController: NavHostController,
    id_khoanChi: Int,
    userId: Int,
    chiTieuViewModel: ChiTieuViewModel = hiltViewModel()
) {
//    val chiTieuState by chiTieuViewModel.uiState.collectAsState()
//    var isRefreshing by remember { mutableStateOf(false) }
//
//    var snackbarVisible by remember { mutableStateOf(false) }
//    var snackbarType by remember { mutableStateOf(SnackbarType.SUCCESS) }
//    var snackbarMessage by remember { mutableStateOf("") }
//
//    val deleteChiTieuState by chiTieuViewModel.deleteChiTieuState.collectAsState()
//
//    LaunchedEffect(deleteChiTieuState) {
//        if (deleteChiTieuState is UiState.Success) {
//            snackbarType = SnackbarType.SUCCESS
//            snackbarMessage = "Xóa chi tiêu thành công"
//            snackbarVisible = true
//
//            chiTieuViewModel.getChiTieuTheoKhoanChiCuaUser(id_khoanChi, userId)
//        } else if (deleteChiTieuState is UiState.Error) {
//            snackbarType = SnackbarType.ERROR
//            snackbarMessage = "Xóa chi tiêu thất bại"
//            snackbarVisible = true
//        }
//    }
//
//    // ✅ Gọi API ban đầu
//    LaunchedEffect(id_khoanChi, userId) {
//        if (userId > 0) {
//            chiTieuViewModel.getChiTieuTheoKhoanChiCuaUser(id_khoanChi, userId)
//        }
//    }
//
//    // ✅ Cập nhật định kỳ 15 phút
//    LaunchedEffect(Unit) {
//        if (userId > 0) {
//            while (true) {
//                delay(15 * 60 * 1000L)
//                chiTieuViewModel.getChiTieuTheoKhoanChiCuaUser(id_khoanChi, userId)
//            }
//        }
//    }
//
//    // ✅ Kéo để refresh
//    val pullRefreshState = rememberPullRefreshState(
//        refreshing = isRefreshing,
//        onRefresh = {
//            isRefreshing = true
//            chiTieuViewModel.getChiTieuTheoKhoanChiCuaUser(id_khoanChi, userId)
//            isRefreshing = false
//        }
//    )
//
//    Scaffold(
//        containerColor = BackgroundColor,
//        topBar = {
//            Header(
//                navController = navController,
//                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
//                title = "Danh sách chi tiêu",
//                userId = userId
//            )
//        },
//        contentWindowInsets = WindowInsets(0, 0, 0, 0)
//    ) { innerPadding ->
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .pullRefresh(pullRefreshState) // ✅ Thêm pull-to-refresh
//        ) {
//            when (val state = chiTieuState) {
//                is UiState.Loading -> {
//                    DotLoading(modifier = Modifier.align(Alignment.Center))
//                }
//
//                is UiState.Success -> {
//                    val chiTieuList = state.data
//                    if (chiTieuList.isEmpty()) {
//                        // ✅ Dù trống vẫn có thể kéo để refresh
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .align(Alignment.Center),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = "Chưa có chi tiêu nào",
//                                color = Color.Gray,
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.Medium
//                            )
//                        }
//                    } else {
//                        LazyColumn(
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .padding(horizontal = 12.dp, vertical = 8.dp),
//                            verticalArrangement = Arrangement.spacedBy(8.dp)
//                        ) {
//                            items(chiTieuList) { chiTieu ->
//                                CardChiTieuSwipeToDelete(
//                                    chitieu = chiTieu,
//                                    onDelete = { deletedItem ->
//                                        chiTieuViewModel.deleteChiTieu(deletedItem.id)
//                                    }
//                                )
//                            }
//                            item { Spacer(modifier = Modifier.height(80.dp)) }
//                        }
//                    }
//                }
//
//                is UiState.Error -> {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = state.message,
//                            color = Color.Gray,
//                            fontSize = 16.sp
//                        )
//                    }
//                }
//
//                else -> Unit
//            }
//
//            PullRefreshIndicator(
//                refreshing = isRefreshing,
//                state = pullRefreshState,
//                modifier = Modifier.align(Alignment.TopCenter)
//            )
//
//            AnimatedVisibility(
//                visible = snackbarVisible,
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = 16.dp),
//                enter = slideInVertically { it } + fadeIn(),
//                exit = slideOutVertically { it } + fadeOut()
//            ) {
//                CustomSnackbar(
//                    message = snackbarMessage,
//                    type = snackbarType
//                )
//            }
//
//            LaunchedEffect(snackbarVisible) {
//                if (snackbarVisible) {
//                    delay(3000)
//                    snackbarVisible = false
//                }
//            }
//        }
//    }
}






@Composable
@Preview
fun KhoanChiDetailPreview(){

}