package com.example.quanlythuchi_android_firestore.data.remote

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeThuNhapModel
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ThuNhapAPIService {
    @GET("api/thunhap/user/{id}/by-month")
    suspend fun getThuNhapTheoThang(
        @Path("id") userId: Int,
        @Query("thang") thang: Int,
        @Query("nam") nam: Int
    ): Response<BaseResponseMes<List<ThuNhapModel>>>


    @GET("api/thunhap/nam/{userId}")
    suspend fun thongKeTheoNam(
        @Path("userId") userId: Int,
        @Query("nam") nam: Int
    ): Response<BaseResponseMes<List<ThongKeThuNhapModel>>>
    @POST("api/thunhap")
    suspend fun createThuNhap(@Body thunhap: ThuNhapModel): Response<BaseResponse<ThuNhapModel>>

    @DELETE("api/thunhap/{id}")
    suspend fun deleteThuNhap(@Path("id") id :Int): Response<StatusResponse>

}