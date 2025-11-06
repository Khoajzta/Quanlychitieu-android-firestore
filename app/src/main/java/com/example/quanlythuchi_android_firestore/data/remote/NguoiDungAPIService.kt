package com.example.quanlythuchi_android_firestore.data.remote

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.CheckEmailResponse
import com.example.quanlythuchi_android_firestore.domain.model.NguoiDungModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface NguoiDungAPIService {
    @POST("api/nguoidung")
    suspend fun postNguoiDung(@Body nguoiDung: NguoiDungModel): Response<BaseResponse<NguoiDungModel>>

    @GET("api/nguoidung/check-email/{email}")
    suspend fun checkEmailNguoiDung(@Path("email") email: String): CheckEmailResponse<NguoiDungModel>

    @GET("api/nguoidung/{id}")
    suspend fun getNguoiDungByID(@Path("id") id :Int): Response<BaseResponseMes<NguoiDungModel>>
}