package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.data.remote.KhoanChiApiService
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.domain.respository.KhoanChiRepository
import javax.inject.Inject

class KhoanChiRepositoryImpl @Inject constructor(
    private val api: KhoanChiApiService
) : KhoanChiRepository {

    override suspend fun getKhoanChi(userId: Int): List<KhoanChiModel> {
        val response = api.getKhoanChiByUser(userId)
        Log.d("API_TEST", "Response: $response")
        if (response.success) {
            return response.data
        } else {
            throw Exception("API trả về success = false")
        }
    }

    override suspend fun getKhoanChiTheoThangVaNam(
        userId: Int,
        thang: Int,
        nam: Int
    ): List<KhoanChiModel> {
        val response = api.getKhoanChiTheoThangVaNam(userId,thang,nam)
        Log.d("API_TEST", "Response: $response")
        if (response.success) {
            return response.data
        } else {
            throw Exception("API trả về success = false")
        }
    }


    override suspend fun getKhoanChiById(id_khoanchi: Int): KhoanChiModel {
        val response = api.getKhoanChiByID(id_khoanchi)
        Log.d("API_TEST", "Response: $response")
        if (response.success) {
            return response.data
        } else {
            throw Exception("API trả về success = false")
        }
    }

    override suspend fun createKhoanChi(khoanchi: KhoanChiModel): BaseResponse<KhoanChiModel> {
        return try {
            val response = api.createKhoanChi(khoanchi)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("API Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            throw Exception("Network/API Error: ${e.message}")
        }
    }

    override suspend fun updateKhoanChi(
        id_khoanchi: Int,
        khoanchi: KhoanChiModel
    ): BaseResponseMes<KhoanChiModel> {
        return try {
            val response = api.updateKhoanChi(id_khoanchi, khoanchi)

            if (response.isSuccessful) {
                val body = response.body() ?: throw Exception("Empty response body")
                BaseResponseMes(
                    success = body.success,
                    data = body.data,
                    message = body.message
                )
            } else {
                val errorMsg = response.errorBody()?.string() ?: "API Error"
                BaseResponseMes(
                    success = false,
                    data = null,
                    message = errorMsg
                )
            }
        } catch (e: Exception) {
            BaseResponseMes(
                success = false,
                data = null,
                message = e.message ?: "Lỗi không xác định"
            )
        }
    }

    override suspend fun deleteKhoanChi(id_khoanchi: Int): StatusResponse {
        return try {
            val response = api.deleteKhoanChi(id_khoanchi)
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
}