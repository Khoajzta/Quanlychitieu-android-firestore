package com.example.quanlythuchi_android_firestore.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlythuchi_android_firestore.Utils.formatCurrency
import com.example.quanlythuchi_android_firestore.Utils.formatDayDisplay
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusLarge
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusXL
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceLarge
import com.example.quanlythuchi_android_firestore.ui.theme.PrimaryColor

@Composable
fun CardKhoanChiDetail(
    modifier: Modifier = Modifier,
    item: KhoanChiModel,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
    onDetailClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    val spentPercentage = remember(item.so_tien_du_kien, item.tong_tien_da_chi) {
        (item.tong_tien_da_chi.toFloat() / item.so_tien_du_kien!!.toFloat()).coerceIn(0f, 1f)
    }

    val isOverLimit = item.tong_tien_da_chi > item.so_tien_du_kien!!

    val backgroundGradientColors = when {
        isOverLimit -> listOf( // ⚠️ nền cảnh báo đỏ
            Color(0xFFFF4B2B),
            Color(0xFFFF416C)
        )
        item.mausac == "red" -> listOf(Color(0xFFE57373), Color(0xFFF06292).copy(alpha = 0.35f))
        item.mausac == "blue" -> listOf(Color(0xFF64B5F6), Color(0xFF4FC3F7).copy(alpha = 0.35f))
        item.mausac == "green" -> listOf(Color(0xFF81C784), Color(0xFF4DB6AC).copy(alpha = 0.35f))
        item.mausac == "yellow" -> listOf(Color(0xFFFFB74D), Color(0xFFFF8A65).copy(alpha = 0.35f))
        else -> listOf(Color(0xFFA0C7E1), Color(0xFFB461CC).copy(alpha = 0.35f))
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(RadiusXL))
            .background(Brush.horizontalGradient(colors = backgroundGradientColors))
            .clickable { expanded = !expanded }
            .animateContentSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = Color.White.copy(0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${item.emoji} ${item.ten_khoanchi}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 15.dp)
                    )
                }

                TextButton(
                    modifier = Modifier,
                    contentPadding = PaddingValues(0.dp),
                    onClick = onDetailClick
                ) {
                    Text(
                        "Xem chi tiết",
                        color = Color.White,
                    )
                    Icon(
                        Icons.Default.ArrowForwardIos,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Hiển thị ngày bắt đầu và kết thúc


            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Dự kiến: ${formatCurrency(item.so_tien_du_kien)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Text(
                    text = if (isOverLimit)
                        "Quá hạn: ${formatCurrency(item.tong_tien_da_chi - item.so_tien_du_kien)}"
                    else
                        "Còn lại: ${formatCurrency(item.so_tien_du_kien - item.tong_tien_da_chi)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.95f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.25f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = formatDayDisplay(item.ngay_batdau!!),
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(20.dp)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.25f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = formatDayDisplay(item.ngay_ketthuc!!),
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Thanh tiến độ
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.2f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(spentPercentage)
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFFFC371),
                                    Color(0xFFFF5F6D)
                                )
                            )
                        )
                )
            }

            // Nút chỉnh sửa / xóa
            AnimatedVisibility(visible = expanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onEdit,
                        modifier = Modifier.padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(0.5f))
                    ) {
                        Text("Sửa", color = Color.Black)
                    }

                    Button(
                        onClick = onDelete,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Xóa", color = Color.White)
                    }
                }
            }
        }
    }
}



@Composable
@Preview
fun CardKhoanChiDetailPreview(){

    CardKhoanChiDetail(
        modifier = Modifier,
        KhoanChiModel(
        id = "1",
        ten_khoanchi = "Tiền ăn",
        id_nguoidung = 1.toString(),
        mausac = "red",
        ngay_batdau = "2025-02-16",
        ngay_ketthuc = "2025-03-16",
        so_tien_du_kien = 3000000,
        tong_tien_da_chi = 500000,
        so_luong_chi_tieu = 5,
        emoji = "🤣"
    ))
}