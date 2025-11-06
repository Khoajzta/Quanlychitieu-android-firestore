package com.example.quanlythuchi_android_firestore.domain.model

data class ChiTieuModel(
    val id: Int,
    val id_nguoidung: Int,
    val id_khoanchi: Int,
    val id_taikhoan: Int,
    val so_tien: Long,
    val ngay_tao: String,
    val ghi_chu: String,
)