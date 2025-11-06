package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.data.remote.ChuyenTienAPIService
import com.example.quanlythuchi_android_firestore.domain.model.ChuyenTienModel
import com.example.quanlythuchi_android_firestore.domain.respository.ChuyenTienRepository
import jakarta.inject.Inject

class ChuyenTienRepositoryImpl @Inject constructor(
    private val api: ChuyenTienAPIService
): ChuyenTienRepository {

    override suspend fun getLichSuChuyenTienByUser(userId: Int): List<ChuyenTienModel> {
        val response = api.getLichSuChuyenTienByUser(userId)
        Log.d("API_TEST", "Response: $response")
        if (response.success) {
            return response.data
        } else {
            throw Exception("API trả về success = false")
        }
    }
}