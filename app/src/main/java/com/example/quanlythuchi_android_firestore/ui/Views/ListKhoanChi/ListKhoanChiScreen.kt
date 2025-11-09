package com.example.quanlythuchi_android_firestore.Views.ListKhoanChi

import android.util.Log
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.CardKhoanChiDetail
import com.example.quanlythuchi_android_firestore.Components.CustomFAB
import com.example.quanlythuchi_android_firestore.Components.DotLoading
import com.example.quanlythuchi_android_firestore.ViewModels.KhoanChiViewModel
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.ui.components.CustomSnackbar
import com.example.quanlythuchi_android_firestore.ui.components.Header
import com.example.quanlythuchi_android_firestore.ui.components.SnackbarType
import com.example.quanlythuchi_android_firestore.ui.components.ThongBaoDialog
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.example.quanlythuchi_android_firestore.ui.theme.BackgroundColor
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceMedium
import kotlinx.coroutines.delay
import java.time.LocalDate

@Composable
fun ListKhoanChiScreen(
    navController: NavController,
    userId: Int,
    khoanChiViewModel: KhoanChiViewModel = hiltViewModel(),
){
    val currentDate = LocalDate.now()
    val currentMonth = currentDate.monthValue
    val currentYear = currentDate.year

    val khoanChiuiState by khoanChiViewModel.loadtheothang.collectAsState()
    val deleteState by khoanChiViewModel.deleteState.collectAsState()

    LaunchedEffect(userId) {
        if (userId > 0) {
            while (true) {
                khoanChiViewModel.getKhoanChiTheThangVaNam(userId.toString(), currentMonth, currentYear)
                delay(15 * 60 * 1000L)
            }
        }
    }

    val khoanChiList = when (khoanChiuiState) {
        is UiState.Success -> (khoanChiuiState as UiState.Success<List<KhoanChiModel>>).data
        else -> emptyList()
    }

    var khoanChiToDelete by remember { mutableStateOf<KhoanChiModel?>(null) }

    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarType by remember { mutableStateOf(SnackbarType.SUCCESS) }
    var snackbarMessage by remember { mutableStateOf("") }

    // 🧭 Dialog xác nhận xóa
    if (khoanChiToDelete != null) {

        ThongBaoDialog(
            title = "Xác nhận xóa",
            message = "Bạn có chắc muốn xóa khoản chi và tất cả các chi tiêu trong đó không?",
            confirmText ="Đồng ý",
            dismissText = "Hủy",
            confirmButtonColor = Color.Red,
            onConfirm = {
                khoanChiToDelete?.let {
//                    khoanChiViewModel.deleteKhoanChi(it.id)
                }
                khoanChiToDelete = null
            },
            onDismiss = {
                khoanChiToDelete = null
            }
        )
    }

    // 🧭 Lắng nghe trạng thái sau khi xóa
    LaunchedEffect(deleteState) {
        when (deleteState) {
            is UiState.Success -> {
                snackbarMessage = "Xóa khoản chi thành công"
                snackbarType = SnackbarType.SUCCESS
                snackbarVisible = true
            }

            is UiState.Error -> {
                snackbarMessage = "Lỗi khi xóa khoản chi"
                snackbarType = SnackbarType.ERROR
                snackbarVisible = true
            }

            else -> Unit
        }
    }

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            Header(
                navController,
                Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = "Danh sách khoản chi",
                userId
            )
        },
        floatingActionButton = {
            CustomFAB(onClick = { navController.navigate(Screen.AddKhoanChi.createRoute(userId)) })
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (khoanChiuiState) {
                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = PaddingBody),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(SpaceMedium)
                    ) {
                        items(khoanChiList){khoanchi->
                            CardKhoanChiDetail(
                                item = khoanchi,
                                modifier = Modifier,
                                onDetailClick = {
//                                    navController.navigate(
//                                        Screen.KhoanChiDetail.createRoute(
//                                            id_khoanChi = khoanchi.id,
//                                            userId = userId
//                                        )
//                                    )
                                },
                                onEdit = {
//                                    navController.navigate(
//                                        Screen.UpdateKhoanChi.createRoute(
//                                            userId,
//                                            id_khoanchi = khoanchi.id
//                                        )
//                                    )
                                },
                                onDelete = {
                                    khoanChiToDelete = khoanchi
                                }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }

                is UiState.Loading -> {
                    DotLoading()
                }

                else -> {
                    Log.d("Error", "Error")
                }
            }

            AnimatedVisibility(
                visible = snackbarVisible,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(bottom = 16.dp),
                enter = slideInVertically { -it } + fadeIn(),
                exit = slideOutVertically { -it } + fadeOut()
            ) {
                CustomSnackbar(
                    message = snackbarMessage,
                    type = snackbarType
                )
            }

            // ⏳ Tự ẩn Snackbar + gọi lại dữ liệu an toàn
            LaunchedEffect(snackbarVisible) {
                if (snackbarVisible) {
                    if (userId > 0) {
                        khoanChiViewModel.getAllKhoanChiByUser(userId.toString())
                    }
                    delay(3000)
                    snackbarVisible = false
                }
            }
        }
    }

}