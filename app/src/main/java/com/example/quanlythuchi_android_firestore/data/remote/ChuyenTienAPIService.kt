package com.example.quanlythuchi_android_firestore.data.remote

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.domain.model.ChuyenTienModel
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ChuyenTienAPIService {

    @GET("api/chuyentien/user/{userId}")
    suspend fun getLichSuChuyenTienByUser(@Path("userId") userId: Int): BaseResponse<List<ChuyenTienModel>>
}