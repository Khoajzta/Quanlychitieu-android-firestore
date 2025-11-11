package com.example.quanlythuchi_android_firestore.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.quanlythuchi_android_firestore.Components.CusTomTextField
import com.example.quanlythuchi_android_firestore.Components.CustomButton
import com.example.quanlythuchi_android_firestore.Utils.formatCurrency
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.TaiKhoanViewModel
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusXL

@Composable
fun CardTaiKhoanChinh(
    modifier: Modifier = Modifier,
    taikhoan: TaiKhoanModel,
    taiKhoanViewModel: TaiKhoanViewModel = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // 🔹 Hiển thị dialog nạp tiền
    if (showDialog) {
        CustomDialog(
            onDismiss = { showDialog = false },
            title = "Nạp tiền"
        ) {
            var sotien by remember { mutableStateOf(0L) }

            CusTomTextField(
                value = if (sotien == 0L) "" else formatCurrency(sotien),
                onValueChange = { newValue ->
                    val digits = newValue.filter { it.isDigit() }
                    sotien = if (digits.isNotEmpty()) digits.toLong() else 0L
                },
                leadingIcon = {
                    Icon(Icons.Default.AttachMoney, contentDescription = null, tint = Color.Gray)
                },
                placeholder = "Số tiền",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButton(
                    title = "Hủy",
                    onClick = { showDialog = false },
                    gradientColors = listOf(Color.Red, Color.Red)
                )
                Spacer(modifier = Modifier.width(5.dp))
                CustomButton(
                    title = "Nạp",
                    onClick = {
                        val taikhoanUpdate = taikhoan.copy(so_du = taikhoan.so_du + sotien)
                        taiKhoanViewModel.updateTaiKhoan(taikhoanUpdate)
                        showDialog = false
                    },
                )
            }
        }
    }

    // 🔹 Card chính
    Box(
        modifier = modifier
            .height(220.dp)
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(RadiusXL))
            .clip(RoundedCornerShape(RadiusXL))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF141E30), Color(0xFF243B55)), // xanh đen sang trọng
                    start = Offset(0f, 0f),
                    end = Offset(400f, 400f)
                )
            )
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 🔹 Hàng đầu: tên tài khoản + logo + icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data("file:///android_asset/icons/ic_wallet.svg")
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = "Main Wallet",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 10.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )

                    Column {
                        Text(
                            text = taikhoan.ten_taikhoan,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = taikhoan.mo_ta,
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "MAIN",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }

            // 🔹 Số dư + nút nạp tiền
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Số dư hiện tại",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Text(
                        text = formatCurrency(taikhoan.so_du),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "**** **** **** ${taikhoan.id.takeLast(4)}",
                        fontSize = 14.sp,
                        letterSpacing = 2.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                CustomButton(
                    title = "Nạp tiền",
                    onClick = { showDialog = true},
                    gradientColors = listOf(Color(0xFF2B81C7), Color(0xFF3FDECF)),
                    icon = Icons.Default.Add
                )

            }
        }
    }
}


@Composable
@Preview
fun CardTaiKhoanChinhPreview() {
    var taikhoan = TaiKhoanModel(
        id = "1",
        id_nguoidung = "1",
        ten_taikhoan = "Tài khoản chính",
        so_du = 2500000,
        loai_taikhoan = 1,
        mo_ta = "Dùng cho chi tiêu hàng ngày"
    )
    CardTaiKhoanChinh(taikhoan = taikhoan)
}