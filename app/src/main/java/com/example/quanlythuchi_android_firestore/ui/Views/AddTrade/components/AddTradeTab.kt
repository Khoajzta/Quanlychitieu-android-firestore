package com.example.quanlythuchi_android_firestore.Views.AddTrade

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.CusTomTextField
import com.example.quanlythuchi_android_firestore.Components.CustomButton
import com.example.quanlythuchi_android_firestore.Components.CustomDatePicker
import com.example.quanlythuchi_android_firestore.Components.CustomDropdown
import com.example.quanlythuchi_android_firestore.Utils.formatCurrency
import com.example.quanlythuchi_android_firestore.Utils.formatMillisToDB
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.ChiTieuViewModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.ThuNhapViewModel
import com.example.quanlythuchi_android_firestore.ui.components.CustomSnackbar
import com.example.quanlythuchi_android_firestore.ui.components.SnackbarType
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceMedium
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddTradeTab(
    navController: NavController,
    listKhoanChi: List<KhoanChiModel>,
    taikhoanchinh: TaiKhoanModel,
    userId: Int
) {
    val tabs = listOf("Chi tiêu", "Thu nhập")
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // --- Tab header ---
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .background(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(25)
                )
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = pagerState.currentPage == index
                    val backgroundColor = if (isSelected) Color.White else Color.Transparent
                    val textColor =
                        if (isSelected) Color(0xFF1C94D5) else Color.Black.copy(alpha = 0.8f)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(25))
                            .background(backgroundColor)
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            color = textColor,
                            fontSize = 15.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- Swipe content area ---
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> AddChiTieuPage(
                    navController = navController,
                    listKhoanChi = listKhoanChi,
                    taikhoanchinh = taikhoanchinh,
                    userId = userId
                )
                1 -> AddThuNhapPage(
                    navController = navController,
                    userId = userId,
                    taikhoanchinh = taikhoanchinh
                )
            }
        }
    }
}


@Composable
fun AddChiTieuPage(
    navController: NavController,
    listKhoanChi: List<KhoanChiModel>,
    taikhoanchinh : TaiKhoanModel,
    userId : Int,
    chiTieuViewModel: ChiTieuViewModel = hiltViewModel()
) {
    var sotien by remember { mutableStateOf(0L) }
    var mota by remember { mutableStateOf("") }
    var selectedKhoanChi by remember { mutableStateOf(listKhoanChi.firstOrNull()) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    // Snackbar state
    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarType by remember { mutableStateOf(SnackbarType.SUCCESS) }
    var snackbarMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PaddingBody)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(SpaceMedium)
        ) {
            CusTomTextField(
                value = if (sotien == 0L) "" else formatCurrency(sotien),
                onValueChange = { newValue ->
                    val digits = newValue.filter { it.isDigit() }
                    sotien = if (digits.isNotEmpty()) digits.toLong() else 0L
                },
                leadingIcon = {
                    Text(
                        text = "💵",
                        fontSize = 20.sp,
                    )
                },
                placeholder = "Số tiền",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            CustomDropdown(
                items = listKhoanChi,
                leadingIcon = {
                    Text(selectedKhoanChi?.emoji ?: "", fontSize = 20.sp)
                },
                selectedItem = selectedKhoanChi,
                itemLabel = { it.ten_khoanchi!! },
                onSelect = { selectedKhoanChi = it }
            )

            CustomDatePicker(
                selectedDate = selectedDate,
                placeholder = "Ngày giao dịch",
                onDateSelected = { selectedDate = it }
            )

            CusTomTextField(
                value = mota,
                onValueChange = { mota = it },
                placeholder = "Nhập ghi chú",
                leadingIcon = {
                    Text(
                        text = "📝",
                        fontSize = 20.sp
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth(),
                title = "Thêm chi tiêu",
                icon = Icons.Default.AddCircle,
                onClick = {

                    if(taikhoanchinh.so_du <= 0){
                        snackbarType = SnackbarType.INFO
                        snackbarMessage = "Số dư trong tài khoản không đủ"
                        snackbarVisible = true
                    }

                    else if (sotien != 0L && selectedDate != null && selectedKhoanChi != null && mota.isNotBlank()) {
//                        val chiTieu = ChiTieuModel(
//                            id = 0,
//                            id_nguoidung = userId,
//                            id_khoanchi = selectedKhoanChi!!.id,
//                            id_taikhoan = taikhoanchinh.id,
//                            so_tien = sotien,
//                            ngay_tao = formatMillisToDB(selectedDate),
//                            ghi_chu = mota
//                        )
//                        chiTieuViewModel.createChiTieu(chiTieu)

                        snackbarType = SnackbarType.SUCCESS
                        snackbarMessage = "Thêm chi tiêu thành công"
                        snackbarVisible = true

                        // reset form nếu muốn
                        sotien = 0
                        mota = ""
                        selectedDate = null
                    } else {
                        snackbarType = SnackbarType.ERROR
                        snackbarMessage = "Vui lòng nhập đầy đủ thông tin"
                        snackbarVisible = true
                    }

                },
            )


        }

        // Hiển thị Snackbar ở cuối màn hình
        AnimatedVisibility(
            visible = snackbarVisible,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut()
        ) {
            CustomSnackbar(
                modifier = Modifier.fillMaxWidth(),
                message = snackbarMessage,
                type = snackbarType
            )
        }

        LaunchedEffect(snackbarVisible) {
            if (snackbarVisible) {
                delay(3000)
                snackbarVisible = false
            }
        }
    }
}





@Composable
fun AddThuNhapPage(
    navController: NavController,
    userId: Int,
    taikhoanchinh : TaiKhoanModel,
    thuNhapViewModel: ThuNhapViewModel = hiltViewModel()
) {
    var tenThuNhap by remember { mutableStateOf("") }
    var sotien by remember { mutableStateOf(0L) }
    var selectedDate by remember { mutableStateOf<Long?>(null) }

    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarType by remember { mutableStateOf(SnackbarType.SUCCESS) }
    var snackbarMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PaddingBody)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(SpaceMedium)
        ) {
            CusTomTextField(
                value = tenThuNhap,
                onValueChange = { tenThuNhap = it },
                leadingIcon = {
                    Text(
                        text = "📝",
                        fontSize = 20.sp
                    )
                },
                placeholder = "Tên thu nhập",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            CusTomTextField(
                value = if (sotien == 0L) "" else formatCurrency(sotien),
                onValueChange = { newValue ->
                    val digits = newValue.filter { it.isDigit() }
                    sotien = if (digits.isNotEmpty()) digits.toLong() else 0L
                },
                leadingIcon = {
                    Text(
                        text = "💵",
                        fontSize = 20.sp,
                    )
                },
                placeholder = "Số tiền",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            CustomDatePicker(
                selectedDate = selectedDate,
                placeholder = "Ngày thu nhập",
                onDateSelected = { selectedDate = it }
            )

            CustomButton(
                modifier = Modifier
                    .fillMaxWidth(),
                title = "Thêm thu nhập",
                icon = Icons.Default.AddCircle,
                onClick = {
                    if(tenThuNhap != "" && sotien != 0L && selectedDate != null ){
                        var thuNhap = ThuNhapModel(
                            id = 0,
                            id_nguoidung = userId,
                            id_taikhoan = taikhoanchinh.id,
                            so_tien = sotien,
                            ngay_tao = formatMillisToDB(selectedDate),
                            ghi_chu = tenThuNhap
                        )


                        thuNhapViewModel.createThuNhap(thuNhap)

                        snackbarType = SnackbarType.SUCCESS
                        snackbarMessage = "Thêm thu nhập thành công"
                        snackbarVisible = true
                    }else{
                        snackbarType = SnackbarType.ERROR
                        snackbarMessage = "Vui lòng nhập đây đủ thông tin"
                        snackbarVisible = true
                    }
                },
            )
        }

        // Hiển thị Snackbar ở cuối màn hình
        AnimatedVisibility(
            visible = snackbarVisible,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut()
        ) {
            CustomSnackbar(
                modifier = Modifier.fillMaxWidth(),
                message = snackbarMessage,
                type = snackbarType
            )
        }

        LaunchedEffect(snackbarVisible) {
            if (snackbarVisible) {
                delay(1500)
                navController.popBackStack()
                snackbarVisible = false
            }
        }
    }

}

@Composable
@Preview
fun Preview(){

//    AddThuNhapPage()
}