package com.example.quanlythuchi_android_firestore.domain.respository

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeChiTieuModel

interface ChiTieuRespository  {

    suspend fun createChiTieu(chitieu: ChiTieuModel) : BaseResponse<ChiTieuModel>
    suspend fun getChiTieuTheoKhoanChiCuaNguoiDung(id_khoanchi: Int, userId: Int): List<ChiTieuModel>
    suspend fun getChiTieuTheoThangVaNam(userId: Int, thang: Int, nam: Int): List<ChiTieuModel>
    suspend fun deleteChiTieu(id: Int): StatusResponse
    suspend fun thongKeTheoNam(userId: Int, nam: Int): BaseResponseMes<List<ThongKeChiTieuModel>>
}
