package com.example.quanlythuchi_android_firestore.domain.model

data class KhoanChiModel(
    val id: Int,
    val ten_khoanchi: String,
    val id_nguoidung: Int, // vì API trả về "1" chứ không phải 1
    val so_tien_du_kien: Long,
    val ngay_batdau: String,
    val ngay_ketthuc: String,
    val mausac: String?,       // nullable
    val emoji: String?,        // nullable
    val so_luong_chi_tieu: Int = 0,
    val tong_tien_da_chi: Long = 0,
)