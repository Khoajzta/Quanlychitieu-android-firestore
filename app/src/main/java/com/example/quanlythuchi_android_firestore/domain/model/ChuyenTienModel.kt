package com.example.quanlythuchi_android_firestore.domain.model

data class ChuyenTienModel(
    val id: String = "",
    val id_nguoidung: String = "",
    val id_taikhoan_nguon: String = "",
    val ten_taikhoan_nguon: String = "",
    val so_du_taikhoan_nguon: Long = 0,
    val id_taikhoan_dich: String = "",
    val ten_taikhoan_dich: String = "",
    val so_tien: Long = 0,
    val ngay_chuyen: String = "",
    val ghi_chu: String = ""
)
