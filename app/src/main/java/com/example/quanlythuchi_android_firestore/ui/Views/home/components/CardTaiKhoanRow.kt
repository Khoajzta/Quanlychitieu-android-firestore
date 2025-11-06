package com.example.quanlythuchi_android_firestore.ui.Views.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.ui.components.CardTaikhoanPhu

@Composable
fun CardTaiKhoanRow(
    modifier: Modifier = Modifier,
    listTaiKhoan: List<TaiKhoanModel>,
    tongTienDuKien :Long = 0L,
    tongThuNhap:Long = 0L,
    tongChiTieu :Long = 0L,
) {
    // Lấy tài khoản chính nếu có, nếu không thì null
    val taiKhoanChinh: TaiKhoanModel? = listTaiKhoan.firstOrNull { it.loai_taikhoan == 1 }


    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Chỉ hiển thị HomeTotalMoney nếu taiKhoanChinh không null
        taiKhoanChinh?.let { tkChinh ->
            item {
                HomeTotalMoney(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = 1f
                            scaleY = 1f
                        },
                    taikhoan = tkChinh,
                    tongTienDuKien = tongTienDuKien,
                    tongThuNhap = tongThuNhap,
                    tongChiTieu = tongChiTieu
                )
            }
        }

        // Hiển thị các tài khoản phụ
        items(listTaiKhoan.let { it.filter { it.loai_taikhoan == 0 } }) { item ->
            CardTaikhoanPhu(modifier = Modifier, item)
        }
    }
}




@Composable
@Preview
fun CardTaiKhoanRowPreviw(){

//    CardTaiKhoanRow(listTaiKhoan = listTaiKhoan)
}