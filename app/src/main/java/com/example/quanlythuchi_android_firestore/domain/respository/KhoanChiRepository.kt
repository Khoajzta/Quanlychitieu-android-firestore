package com.example.quanlythuchi_android_firestore.domain.respository

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel

interface KhoanChiRepository {
    suspend fun getKhoanChi(userId: Int): List<KhoanChiModel>
    suspend fun getKhoanChiTheoThangVaNam(userId: Int, thang:Int, nam:Int): List<KhoanChiModel>
    suspend fun getKhoanChiById(id_khoanchi: Int): KhoanChiModel
    suspend fun createKhoanChi(khoanchi : KhoanChiModel) : BaseResponse<KhoanChiModel>
    suspend fun updateKhoanChi(id_khoanchi: Int, khoanchi: KhoanChiModel): BaseResponseMes<KhoanChiModel>
    suspend fun deleteKhoanChi(id_khoanchi: Int): StatusResponse
}