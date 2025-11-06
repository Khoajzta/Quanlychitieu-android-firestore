package com.example.quanlythuchi_android_firestore.domain.model

data class ChuyenTienModel(
    val id: Int,
    val id_nguoidung: Int,
    val id_taikhoan_nguon: Int,
    val ten_taikhoan_nguon: String? = "",
    val so_du_taikhoan_nguon: Long = 0,
    val id_taikhoan_dich: Int,
    val ten_taikhoan_dich: String? = "",
    val so_tien: Long,
    val ngay_chuyen: String,
    val ghi_chu: String,
)