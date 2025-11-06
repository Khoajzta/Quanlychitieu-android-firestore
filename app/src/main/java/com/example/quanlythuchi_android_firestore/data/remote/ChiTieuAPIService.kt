package com.example.quanlythuchi_android_firestore.data.remote

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeChiTieuModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChiTieuAPIService {
    @POST("api/chitieu")
    suspend fun postChiTieu(@Body chitieu: ChiTieuModel): Response<BaseResponse<ChiTieuModel>>

    @GET("api/chitieu/khoanchi/{id_khoanchi}/user/{userId}")
    suspend fun getChiTieuTheoKhoanChiCuaNguoiDung(
        @Path("id_khoanchi") id_khoanchi: Int,
        @Path("userId") userId: Int,
    ): BaseResponse<List<ChiTieuModel>>

    @GET("api/chitieu/user/{userId}/by-month")
    suspend fun getChiTieuTheoThangVaNam(
        @Path("userId") userId: Int,
        @Query("thang") thang: Int,
        @Query("nam") nam: Int
    ): BaseResponse<List<ChiTieuModel>>

    @GET("api/chitieu/nam/{userId}")
    suspend fun thongkeTheoNam(
        @Path("userId") userId: Int,
        @Query("nam") nam: Int
    ): Response<BaseResponseMes<List<ThongKeChiTieuModel>>>

    @DELETE("api/chitieu/{id}")
    suspend fun deleteChiTieu(
        @Path("id") id: Int
    ): Response<StatusResponse>
}