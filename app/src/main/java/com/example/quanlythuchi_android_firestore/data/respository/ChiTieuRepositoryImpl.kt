package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.data.remote.ChiTieuAPIService
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.respository.ChiTieuRespository
import javax.inject.Inject

class ChiTieuRepositoryImpl @Inject constructor(
    private val api: ChiTieuAPIService
): ChiTieuRespository {
    override suspend fun createChiTieu(chitieu: ChiTieuModel): BaseResponse<ChiTieuModel> {
        return try {
            val response = api.postChiTieu(chitieu)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("API Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            throw Exception("Network/API Error: ${e.message}")
        }
    }

    override suspend fun getChiTieuTheoKhoanChiCuaNguoiDung(
        id_khoanchi: Int,
        userId: Int
    ): List<ChiTieuModel> {
        val response = api.getChiTieuTheoKhoanChiCuaNguoiDung(id_khoanchi = id_khoanchi, userId = userId)
        Log.d("API_TEST", "Response: $response")
        if (response.success) {
            return response.data
        } else {
            throw Exception("API trả về success = false")
        }
    }

    override suspend fun getChiTieuTheoThangVaNam(
        userId: Int,
        thang: Int,
        nam: Int
    ): List<ChiTieuModel> {
        val response = api.getChiTieuTheoThangVaNam(userId = userId, thang = thang, nam = nam)
        Log.d("API_TEST", "Response: $response")
        if (response.success) {
            return response.data
        } else {
            throw Exception("API trả về success = false")
        }
    }

    override suspend fun deleteChiTieu(id: Int): StatusResponse {
        return try {
            val response = api.deleteChiTieu(id)
            if (response.isSuccessful) {
                response.body() ?: StatusResponse(
                    success = false,
                    message = "Empty response"
                )
            } else {
                StatusResponse(
                    success = false,
                    message = response.errorBody()?.string() ?: "API Error"
                )
            }
        } catch (e: Exception) {
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định"
            )
        }
    }

    override suspend fun thongKeTheoNam(
        userId: Int,
        nam: Int
    ): BaseResponseMes<List<ThongKeChiTieuModel>> {
        val response = api.thongkeTheoNam(userId, nam)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("API error ${response.code()}")
        }
    }
}