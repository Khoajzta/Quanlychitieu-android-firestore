package com.example.quanlythuchi_android_firestore.domain.model

data class KhoanChiModel(
    val id: String? = null,
    val ten_khoanchi: String? = null,
    val id_nguoidung: String? = null,
    val so_tien_du_kien: Long? = null,
    val ngay_batdau: String? = null,
    val ngay_ketthuc: String? = null,
    val mausac: String? = null,
    val emoji: String? = null,
    val so_luong_chi_tieu: Int = 0,
    val tong_tien_da_chi: Long = 0
)
