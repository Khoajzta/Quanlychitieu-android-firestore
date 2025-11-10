package com.example.quanlythuchi_android_firestore.data.remote.dto

data class ChuyenTienRequest (
    var fromId : String,
    var toId: String,
    var amount:Long,
    var id_nguoidung: String,
    var ghi_chu: String
)