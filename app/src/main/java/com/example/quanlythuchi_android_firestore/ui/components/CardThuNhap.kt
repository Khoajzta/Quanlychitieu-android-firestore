package com.example.quanlythuchi_android_firestore.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlythuchi_android_firestore.Utils.formatCurrency
import com.example.quanlythuchi_android_firestore.Utils.formatDayDisplay
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel
import com.example.quanlythuchi_android_firestore.ui.components.ThongBaoDialog
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusLarge
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusXL

@Composable
fun CardThuNhap(
    thuNhap: ThuNhapModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF189D80), Color(0xFF4EBD7F), Color(0xFF90EFB7)),
                    start = Offset(0f, 0f),
                    end = Offset(300f, 300f)
                ),
                shape = RoundedCornerShape(RadiusXL)
            )
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
            ) {

                Text(
                    text = "${thuNhap.ghi_chu}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Ngày: ${formatDayDisplay(thuNhap.ngay_tao)}",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.85f),
                )
            }


            Box(
                modifier = Modifier
                    .background(
                        Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)

            ) {
                Text(
                    text = "💵 ${formatCurrency(thuNhap.so_tien)}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF057968)
                )
            }

        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardThuNhapSwipeToDelete(
    thuNhap: ThuNhapModel,
    onDelete: (ThuNhapModel) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    // State điều khiển swipe
    val dismissState = rememberDismissState(
        confirmStateChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                // Vuốt sang trái xong -> hiện dialog xác nhận
                showDialog = true
            }
            false // Không xóa thẻ ngay lập tức
        }
    )

    if (showDialog) {

        ThongBaoDialog(
            title = "Xác nhận xóa thu nhập",
            message = "Bạn có chắc muốn xóa thu nhập này không?",
            onConfirm = {onDelete(thuNhap)
                showDialog = false},
            onDismiss = {showDialog = false},
            confirmText = "Đồng ý",
            dismissText = "Hủy",
            confirmButtonColor = Color.Red
        )
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            // Nền đỏ khi vuốt sang trái
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red, shape = RoundedCornerShape(RadiusXL))
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Xóa",
                    tint = Color.White
                )
            }
        },
        dismissContent = {
            CardThuNhap(thuNhap = thuNhap)
        }
    )
}



@Composable
@Preview
fun CardThuNhapPreview() {
    CardThuNhap(
        thuNhap = ThuNhapModel(
            id = 1,
            id_nguoidung = 21,
            id_taikhoan = 1,
            so_tien = 1000000,
            ngay_tao = "2025-09-15",
            ghi_chu = "Tiền lương"
        )
    )
}
