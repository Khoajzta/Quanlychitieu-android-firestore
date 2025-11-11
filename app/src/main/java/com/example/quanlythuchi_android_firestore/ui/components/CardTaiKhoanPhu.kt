package com.example.quanlythuchi_android_firestore.ui.components

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.quanlythuchi_android_firestore.Utils.formatCurrency
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusXL

@Composable
fun CardTaikhoanPhu(
    modifier: Modifier = Modifier,
    taikhoan: TaiKhoanModel,
    iconPath: String = "file:///android_asset/icons/ic_wallet.svg", // icon ví hoặc ngân hàng
    fullWidth: Boolean = false
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .height(220.dp)
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier.width(370.dp))
            .shadow(6.dp, RoundedCornerShape(RadiusXL))
            .clip(RoundedCornerShape(RadiusXL))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF56CCF2), Color(0xFF2F80ED)), // xanh gradient hiện đại
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

            // Hàng trên: Tên tài khoản + icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(iconPath)
                            .decoderFactory(SvgDecoder.Factory())
                            .build(),
                        contentDescription = taikhoan.ten_taikhoan,
                        modifier = Modifier
                            .size(36.dp)
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
                            fontSize = 16.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                // Chip góc phải
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "BANK",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }

            // Khoảng cách
            Spacer(modifier = Modifier.height(8.dp))

            // Số dư
            Column {
                Text(
                    text = "Số dư",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Text(
                    text = formatCurrency(taikhoan.so_du),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "**** **** **** ${taikhoan.id.toString().takeLast(4)}",
                    fontSize = 14.sp,
                    letterSpacing = 2.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardTaiKhoanSwipeToDelete(
    taikhoan: TaiKhoanModel,
    onDelete: (TaiKhoanModel) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    // Trạng thái swipe
    val dismissState = rememberDismissState(
        confirmStateChange = { value ->
            if (value == DismissValue.DismissedToStart) {
                showDialog = true
            }
            false // Không xóa ngay
        }
    )

    // Dialog xác nhận xóa
    if (showDialog) {
        ThongBaoDialog(
            title = "Xác nhận xóa tài khoản",
            message = "Bạn có chắc muốn xóa tài khoản '${taikhoan.ten_taikhoan}' không?",
            onConfirm = {
                onDelete(taikhoan)
                showDialog = false
            },
            onDismiss = { showDialog = false },
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
            CardTaikhoanPhu(
                taikhoan = taikhoan,
                fullWidth = true
            )
        }
    )
}



@Composable
@Preview
fun CardTaiKhoanPhuPreview() {
   var taikhoan = TaiKhoanModel(
        id = "1",
        id_nguoidung = "1",
        ten_taikhoan = "Tiền mua xe",
        so_du = 2500000,
        loai_taikhoan = 1,
        mo_ta = "Tiền để dành mua xe"
    )
    CardTaiKhoanSwipeToDelete(taikhoan = taikhoan, onDelete = {})
}
