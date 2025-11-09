package com.example.quanlythuchi_android_firestore.domain.respository

import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel

interface KhoanChiRepository {
    suspend fun getKhoanChi(userId: String): List<KhoanChiModel>
    suspend fun getKhoanChiTheoThangVaNam(userId: String, thang:Int, nam:Int): List<KhoanChiModel>
    suspend fun getKhoanChiById(id_khoanchi: String): KhoanChiModel
    suspend fun createKhoanChi(khoanchi : KhoanChiModel) : StatusResponse
    suspend fun updateKhoanChi(khoanchi: KhoanChiModel): StatusResponse
    suspend fun deleteKhoanChi(id_khoanchi: String): StatusResponse
}