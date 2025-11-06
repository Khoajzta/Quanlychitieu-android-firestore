package com.example.quanlythuchi_android_firestore.domain.model

data class TaiKhoanModel(
    val id: Int,
    val id_nguoidung: Int,
    val ten_taikhoan: String,
    val so_du: Long,
    val loai_taikhoan: Int,
    val mo_ta: String,
)