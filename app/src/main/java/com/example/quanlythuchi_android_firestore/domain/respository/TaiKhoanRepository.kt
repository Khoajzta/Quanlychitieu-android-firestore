package com.example.quanlythuchi_android_firestore.domain.respository

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.ChuyenTienRequest
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel

interface TaiKhoanRepository {
    suspend fun getTaiKhoanNguoiDung(userId: String) : List<TaiKhoanModel>
    suspend fun getTaiKhoanChinhNguoiDung(userId: String) : TaiKhoanModel
    suspend fun createTaiKhoan(taikhoan: TaiKhoanModel): BaseResponseMes<TaiKhoanModel>
    suspend fun updateTaiKhoan(taikhoan: TaiKhoanModel): StatusResponse
    suspend fun chuyenTien(chuyenTienRequest: ChuyenTienRequest) : StatusResponse
    suspend fun deleteTaiKhoan(id: String): StatusResponse
}