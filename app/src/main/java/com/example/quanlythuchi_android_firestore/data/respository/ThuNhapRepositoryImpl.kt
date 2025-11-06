package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.data.remote.TaiKhoanAPIService
import com.example.quanlythuchi_android_firestore.data.remote.ThuNhapAPIService
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeThuNhapModel
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel
import com.example.quanlythuchi_android_firestore.domain.respository.ThuNhapRepository
import javax.inject.Inject

class ThuNhapRepositoryImpl @Inject constructor(
    private val api: ThuNhapAPIService
) : ThuNhapRepository {

    override suspend fun getThuNhapTheoThang(
        userId: Int,
        thang: Int,
        nam: Int
    ): BaseResponseMes<List<ThuNhapModel>> {
        val response = api.getThuNhapTheoThang(userId, thang, nam)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("API error ${response.code()}")
        }
    }

    override suspend fun thongkeTheoNam(
        userId: Int,
        nam: Int
    ): BaseResponseMes<List<ThongKeThuNhapModel>> {
        val response = api.thongKeTheoNam(userId, nam)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Empty body")
        } else {
            throw Exception("API error ${response.code()}")
        }
    }

    override suspend fun createThuNhap(thuNhapModel: ThuNhapModel): BaseResponse<ThuNhapModel> {
        return try {
            val response = api.createThuNhap(thuNhapModel)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("API Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            throw Exception("Network/API Error: ${e.message}")
        }
    }

    override suspend fun deleteThuNhap(id: Int): StatusResponse {
        return try {
            val response = api.deleteThuNhap(id)
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