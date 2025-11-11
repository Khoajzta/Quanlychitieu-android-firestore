package com.example.quanlythuchi_android_firestore.ui.Views.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlythuchi_android_firestore.R
import com.example.quanlythuchi_android_firestore.Utils.formatCurrency
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusXL
import java.time.LocalDate

@Composable
fun HomeTotalMoney(
    modifier: Modifier = Modifier,
    taikhoan: TaiKhoanModel,
    tongTienDuKien: Long,
    tongThuNhap: Long,
    tongChiTieu: Long
) {
    val currentDate = LocalDate.now()
    val currentMonth = currentDate.monthValue
    val currentYear = currentDate.year

    Box(
        modifier = modifier
            .width(370.dp)
            .wrapContentHeight()
            .shadow(8.dp, RoundedCornerShape(28.dp))
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF65B3E8), Color(0xFF8488E8)), // gradient pastel
                    start = Offset(0f, 0f),
                    end = Offset(400f, 400f)
                )
            )
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 🧾 Phần trên: Tổng số dư
            Column {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Tổng số dư",
                        color = Color.White.copy(0.7f),
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Tháng ${currentMonth} năm ${currentYear}",
                        color = Color.White.copy(0.7f),
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = formatCurrency(taikhoan.so_du),
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Khoản chi dự kiến",
                    color = Color.White.copy(0.7f),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = formatCurrency(tongTienDuKien),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            // 💰 Phần dưới: Thống kê thu / chi trong tháng
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.White.copy(0.15f), Color.White.copy(0.08f))
                        )
                    )
                    .padding(vertical = 10.dp, horizontal = 15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ArrowUpward, contentDescription = null, tint = Color(0xFF00E676))

                        Spacer(Modifier.width(6.dp))
                        Column {
                            Text(
                                text = "Thu nhập tháng",
                                color = Color.White.copy(0.7f),
                                fontSize = 12.sp
                            )
                            Text(
                                text = formatCurrency(tongThuNhap),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ArrowDownward, contentDescription = null, tint = Color(0xFFFF5252))
                        Spacer(Modifier.width(6.dp))
                        Column {
                            Text(
                                text = "Chi tiêu tháng",
                                color = Color.White.copy(0.7f),
                                fontSize = 12.sp
                            )
                            Text(
                                text = formatCurrency(tongChiTieu),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun PreviewHomeTotalMoney() {

    var taikhoan =TaiKhoanModel(
        id = "1",
        id_nguoidung = "1",
        ten_taikhoan = "Tiền mua xe",
        so_du = 2500000,
        loai_taikhoan = 1,
        mo_ta = "Tiền để dành mua xe"
    )
    HomeTotalMoney(
        taikhoan = taikhoan,
        modifier = Modifier,
        tongTienDuKien = 3000,
        tongThuNhap = 30000000,
        tongChiTieu = 3000000
    )
}