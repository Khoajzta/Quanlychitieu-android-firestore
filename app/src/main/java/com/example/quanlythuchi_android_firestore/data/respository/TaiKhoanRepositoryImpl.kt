package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.data.remote.TaiKhoanAPIService
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.ChuyenTienRequest
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.domain.respository.TaiKhoanRepository
import javax.inject.Inject

class TaiKhoanRepositoryImpl@Inject constructor(
    private val api: TaiKhoanAPIService
): TaiKhoanRepository {
    override suspend fun getTaiKhoanNguoiDung(userId: Int): List<TaiKhoanModel> {
        val response = api.getTaiKhoanNguoiDung(userId)
        Log.d("API_TEST", "Response: $response")
        if (response.success) {
            return response.data
        } else {
            throw Exception("API trả về success = false")
        }
    }

    override suspend fun createaTaiKhoan(taikhoan: TaiKhoanModel): BaseResponseMes<TaiKhoanModel> {
        return try {
            val response = api.createTaiKhoan(taikhoan)
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Empty response body")
            } else {
                throw Exception("API Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            throw Exception("Network/API Error: ${e.message}")
        }
    }

    override suspend fun updateTaiKhoan(
        taikhoan: TaiKhoanModel,
        id: Int
    ): BaseResponseMes<TaiKhoanModel> {
        return try {
            val response = api.updateTaiKhoan(taikhoan, id)
            if(response.isSuccessful){
                response.body() ?: throw Exception("Empty response body")
            }else{
                throw Exception("API Error: ${response.errorBody()?.string()}")
            }

        }catch (e: Exception){
            throw Exception("Network/API Error: ${e.message}")
        }
    }

    override suspend fun chuyenTien(chuyenTienRequest: ChuyenTienRequest): StatusResponse {
        return try {
            val response = api.chuyenTien(chuyenTienRequest)
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

    override suspend fun deleteTaiKhoan(id: Int): StatusResponse {
        return try {
            val response = api.deleteTaiKhoan(id)
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