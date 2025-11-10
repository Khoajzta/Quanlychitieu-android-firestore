package com.example.quanlythuchi_android_firestore.ui.Views.UpdateKhoanChi

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.emoji2.text.EmojiCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.CusTomTextField
import com.example.quanlythuchi_android_firestore.Components.CustomButton
import com.example.quanlythuchi_android_firestore.Components.CustomDatePicker
import com.example.quanlythuchi_android_firestore.Components.DotLoading
import com.example.quanlythuchi_android_firestore.Utils.formatCurrency
import com.example.quanlythuchi_android_firestore.Utils.formatMillisToDB
import com.example.quanlythuchi_android_firestore.Utils.parseDateToMillis
import com.example.quanlythuchi_android_firestore.ViewModels.KhoanChiViewModel
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.ui.Views.AddKhoanChi.components.ColorPickerRow
import com.example.quanlythuchi_android_firestore.ui.Views.AddKhoanChi.components.EmojiRow
import com.example.quanlythuchi_android_firestore.ui.components.CustomSnackbar
import com.example.quanlythuchi_android_firestore.ui.components.EmojiPickerBottomSheet
import com.example.quanlythuchi_android_firestore.ui.components.Header
import com.example.quanlythuchi_android_firestore.ui.components.SnackbarType
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.example.quanlythuchi_android_firestore.ui.theme.BackgroundColor
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceMedium
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKhoanChiScreen(
    navController: NavController,
    id_khoanChi: String,
    userId: String,
    khoanChiViewModel: KhoanChiViewModel = hiltViewModel()
) {
    val khoanChiState by khoanChiViewModel.getById.collectAsState()
    val updateState by khoanChiViewModel.updateState.collectAsState()

    // State UI
    var tenKhoanChi by remember { mutableStateOf("") }
    var soTien by remember { mutableStateOf(0L) }
    var selectedColor by remember { mutableStateOf("") }
    var emoji by remember { mutableStateOf("") }
    var ngayBatDau by remember { mutableStateOf<Long?>(null) }
    var ngayKetThuc by remember { mutableStateOf<Long?>(null) }

    var showEmojiDialog by remember { mutableStateOf(false) }
    val emojiSuggestions = listOf("🍔", "☕", "🛒", "🎁", "✈️")
    val colorOptions = listOf("red", "blue", "green", "yellow")

    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarType by remember { mutableStateOf(SnackbarType.SUCCESS) }
    var snackbarMessage by remember { mutableStateOf("") }

    // 🔹 Lấy dữ liệu khi mở màn hình
    LaunchedEffect(id_khoanChi) {
        khoanChiViewModel.getKhoanChiById(id_khoanChi.toString())
    }

    // 🔹 Gán dữ liệu khi API thành công
    LaunchedEffect(khoanChiState) {
        if (khoanChiState is UiState.Success) {
            with((khoanChiState as UiState.Success<KhoanChiModel>).data) {
                tenKhoanChi = ten_khoanchi!!
                soTien = so_tien_du_kien!!
                selectedColor = mausac ?: ""
                emoji = this.emoji ?: ""
                ngayBatDau = parseDateToMillis(ngay_batdau!!)
                ngayKetThuc = parseDateToMillis(ngay_ketthuc!!)
            }
        }
    }

    // 🔹 Lắng nghe trạng thái cập nhật
    LaunchedEffect(updateState) {
        when (updateState) {
            is UiState.Success -> {
                snackbarMessage = "Cập nhật khoản chi thành công"
                snackbarType = SnackbarType.SUCCESS
                snackbarVisible = true
                delay(1000)
                navController.popBackStack()
            }

            is UiState.Error -> {
                snackbarMessage = (updateState as UiState.Error).message
                snackbarType = SnackbarType.ERROR
                snackbarVisible = true
                delay(2500)
                snackbarVisible = false
            }

            else -> Unit
        }
    }

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            Header(
                navController = navController,
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = "Cập nhật khoản chi"
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
            when (khoanChiState) {
                is UiState.Loading -> DotLoading()

                is UiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = PaddingBody),
                        verticalArrangement = Arrangement.spacedBy(SpaceMedium)
                    ) {
                        // 🏷️ Tên khoản chi + emoji
                        CusTomTextField(
                            value = tenKhoanChi,
                            onValueChange = { tenKhoanChi = it },
                            leadingIcon = {
                                Text(
                                    text = EmojiCompat.get().process(emoji).toString(),
                                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                                )
                            },
                            placeholder = "Tên khoản chi",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // 📅 Ngày bắt đầu & kết thúc
                        CustomDatePicker(
                            selectedDate = ngayBatDau,
                            placeholder = "Ngày bắt đầu",
                            onDateSelected = { ngayBatDau = it }
                        )
                        CustomDatePicker(
                            selectedDate = ngayKetThuc,
                            placeholder = "Ngày kết thúc",
                            onDateSelected = { ngayKetThuc = it }
                        )

                        // 💰 Số tiền
                        CusTomTextField(
                            value = if (soTien == 0L) "" else formatCurrency(soTien),
                            onValueChange = {
                                val digits = it.filter(Char::isDigit)
                                soTien = digits.toLongOrNull() ?: 0L
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.AttachMoney,
                                    contentDescription = null,
                                    tint = Color.Gray
                                )
                            },
                            placeholder = "Số tiền",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // 😀 Emoji + màu
                        EmojiRow(
                            listEmoji = emojiSuggestions,
                            onClickEmoji = { emoji = it },
                            onClickMore = { showEmojiDialog = true }
                        )

                        ColorPickerRow(
                            colorOptions = colorOptions,
                            selectedColor = selectedColor,
                            onColorSelected = { selectedColor = it }
                        )

                        // 💾 Nút lưu
                        CustomButton(
                            modifier = Modifier.fillMaxWidth(),
                            title = "Lưu khoản chi",
                            onClick = {
                                khoanChiViewModel.updateKhoanChi(
                                    khoanchi = KhoanChiModel(
                                        id = id_khoanChi.toString(),
                                        ten_khoanchi = tenKhoanChi,
                                        id_nguoidung = userId.toString(),
                                        mausac = selectedColor,
                                        ngay_batdau = formatMillisToDB(ngayBatDau),
                                        ngay_ketthuc = formatMillisToDB(ngayKetThuc),
                                        so_tien_du_kien = soTien,
                                        emoji = emoji
                                    )
                                )
                            }
                        )
                    }
                }

                is UiState.Error -> Text("Lỗi: ${(khoanChiState as UiState.Error).message}")
            }

            // 🎭 Dialog emoji
            EmojiPickerBottomSheet(
                show = showEmojiDialog,
                onDismiss = { showEmojiDialog = false },
                onEmojiSelected = {
                    emoji = it
                    showEmojiDialog = false
                }
            )


            // 🪄 Snackbar
            AnimatedVisibility(
                visible = snackbarVisible,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                CustomSnackbar(message = snackbarMessage, type = snackbarType)
            }
        }
    }
}



@Composable
@Preview
fun UpdateKhoanChiScreenPreview(){

}