package com.example.quanlythuchi_android_firestore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlythuchi_android_firestore.Utils.formatCurrency
import com.example.quanlythuchi_android_firestore.Utils.formatDayDisplay
import com.example.quanlythuchi_android_firestore.domain.model.ChuyenTienModel

@Composable
fun CardLichSuChuyenTien(
    chuyenTien: ChuyenTienModel,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE3F2FD),
                        Color(0xFFE8F5E9)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )

            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Hàng đầu: icon + tài khoản nguồn & đích
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF4CAF50),
                                    Color(0xFF81C784)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SyncAlt,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Từ: ${chuyenTien.ten_taikhoan_nguon ?: "TK #${chuyenTien.id_taikhoan_nguon}"}",
                        color = Color(0xFF1A237E),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Đến: ${chuyenTien.ten_taikhoan_dich ?: "TK #${chuyenTien.id_taikhoan_dich}"}",
                        color = Color(0xFF2E7D32),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )

                    if (!chuyenTien.ghi_chu.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = chuyenTien.ghi_chu,
                            color = Color(0xFF616161),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "+${formatCurrency(chuyenTien.so_tien)}",
                    color = Color(0xFF1B5E20),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Divider(color = Color(0xFFBDBDBD))

            // Hàng cuối: ngày và số dư tài khoản nguồn
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = formatDayDisplay(chuyenTien.ngay_chuyen),
                    color = Color(0xFF757575),
                    fontSize = 12.sp
                )

                if (chuyenTien.so_du_taikhoan_nguon != null) {
                    Text(
                        text = "SD: ${formatCurrency(chuyenTien.so_du_taikhoan_nguon)}",
                        color = Color(0xFF757575),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}





@Composable
@Preview
fun CardLichSuChuyenTienPreview(){

    val chuyenTienModel = ChuyenTienModel(
        id = 1,
        id_nguoidung = 1,
        id_taikhoan_nguon = 1,
        id_taikhoan_dich = 2,
        so_tien = 100000,
        ngay_chuyen = "2025-09-11",
        ghi_chu = "Chuyển vào tài khoản mua xe",
        ten_taikhoan_nguon = "Tài khoản chính",
        ten_taikhoan_dich = "Mua xe",
        so_du_taikhoan_nguon = 3000000
    )
    CardLichSuChuyenTien(
        chuyenTien = chuyenTienModel
    )
}

