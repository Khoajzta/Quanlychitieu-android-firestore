package com.example.quanlythuchi_android_firestore.domain.model

import com.google.firebase.Timestamp

data class ChiTieuModel(
    var id: String? = null,
    var id_nguoidung: String? = null,
    var id_taikhoan: String? = null,
    var id_khoanchi: String? = null,
    var id_taikhoan_dich: String? = null,
    var id_taikhoan_nguon: String? = null,
    var so_tien: Long? = null,
    var ghi_chu: String? = null,
    var ngay_tao: String? =null
)