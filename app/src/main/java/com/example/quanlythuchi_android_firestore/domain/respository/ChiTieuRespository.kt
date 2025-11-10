package com.example.quanlythuchi_android_firestore.domain.respository

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeChiTieuModel

interface ChiTieuRepository {


    suspend fun getChiTieuTheoKhoanChiCuaNguoiDung(
        id_khoanchi: String,
        userId: String
    ): BaseResponse<List<ChiTieuModel>>

    suspend fun getChiTieuTheoThangVaNam(
        userId: String,
        thang: Int,
        nam: Int
    ): List<ChiTieuModel>

    suspend fun createChiTieu(chitieu: ChiTieuModel): StatusResponse
    suspend fun updateChiTieu(chitieu: ChiTieuModel): StatusResponse
    suspend fun deleteChiTieu(id: String): StatusResponse

    suspend fun thongKeTheoNam(
        userId: String,
        nam: Int
    ): BaseResponseMes<List<ThongKeChiTieuModel>>


}