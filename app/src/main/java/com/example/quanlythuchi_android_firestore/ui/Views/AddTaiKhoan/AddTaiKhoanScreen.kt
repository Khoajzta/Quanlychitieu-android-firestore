package com.example.quanlythuchi_android_firestore.ui.Views.AddTaiKhoan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.CusTomTextField
import com.example.quanlythuchi_android_firestore.Components.CustomButton
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.TaiKhoanViewModel
import com.example.quanlythuchi_android_firestore.ui.components.CustomSnackbar
import com.example.quanlythuchi_android_firestore.ui.components.Header
import com.example.quanlythuchi_android_firestore.ui.components.SnackbarType
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.example.quanlythuchi_android_firestore.ui.theme.BackgroundColor
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceMedium
import kotlinx.coroutines.delay

@Composable
fun AddTaiKhoanScreen(
    navController: NavController,
    userId: String,
    taiKhoanViewModel: TaiKhoanViewModel = hiltViewModel()
) {
    var tenTaiKhoan by remember { mutableStateOf("") }
    var moTa by remember { mutableStateOf("") }

    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarType by remember { mutableStateOf(SnackbarType.SUCCESS) }
    var snackbarMessage by remember { mutableStateOf("") }

    val addTaiKhoanState by taiKhoanViewModel.createTaiKhoanState.collectAsState()

    // Theo dõi trạng thái thêm tài khoản
    LaunchedEffect(addTaiKhoanState) {
        when (addTaiKhoanState) {
            is UiState.Success -> {
                snackbarType = SnackbarType.SUCCESS
                snackbarMessage = "Tạo tài khoản thành công!"
                snackbarVisible = true
            }
            is UiState.Error -> {
                snackbarType = SnackbarType.ERROR
                snackbarMessage = (addTaiKhoanState as UiState.Error).message ?: "Có lỗi xảy ra!"
                snackbarVisible = true
            }
            else -> {}
        }
    }

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            Header(
                navController = navController,
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = "Tạo tài khoản",
                userId = userId
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingBody),
                verticalArrangement = Arrangement.spacedBy(SpaceMedium)
            ) {
                CusTomTextField(
                    value = tenTaiKhoan,
                    onValueChange = { tenTaiKhoan = it },
                    leadingIcon = {
                        Text(
                            text = "📝",
                            fontSize = 20.sp
                        )
                    },
                    placeholder = "Tên tài khoản",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                CusTomTextField(
                    value = moTa,
                    onValueChange = { moTa = it },
                    leadingIcon = {
                        Text(
                            text = "📝",
                            fontSize = 20.sp
                        )
                    },
                    placeholder = "Mô tả",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = "Tạo tài khoản",
                    onClick = {
                        when {
                            tenTaiKhoan.isBlank() -> {
                                snackbarType = SnackbarType.INFO
                                snackbarMessage = "Vui lòng nhập tên tài khoản!"
                                snackbarVisible = true
                            }
                            moTa.isBlank() -> {
                                snackbarType = SnackbarType.INFO
                                snackbarMessage = "Vui lòng nhập mô tả!"
                                snackbarVisible = true
                            }
                            else -> {
                                taiKhoanViewModel.createTaiKhoan(
                                    TaiKhoanModel(
                                        id = "",
                                        ten_taikhoan = tenTaiKhoan,
                                        mo_ta = moTa,
                                        so_du = 0L,
                                        id_nguoidung = userId,
                                        loai_taikhoan = 0
                                    )
                                )
                            }
                        }
                    },
                    icon = Icons.Default.AddCircle
                )
            }

            // Snackbar hiển thị ở cuối màn hình
            AnimatedVisibility(
                visible = snackbarVisible,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                CustomSnackbar(
                    message = snackbarMessage,
                    type = snackbarType
                )
            }
        }
    }

    // Tự động ẩn snackbar và quay lại sau khi tạo thành công
    LaunchedEffect(snackbarVisible, snackbarType) {
        if (snackbarVisible && snackbarType == SnackbarType.SUCCESS) {
            delay(1500)
            snackbarVisible = false
            navController.popBackStack()
        } else if (snackbarVisible) {
            delay(1500)
            snackbarVisible = false
        }
    }
}
