package com.example.quanlythuchi_android_firestore.data.remote

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.ChuyenTienRequest
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaiKhoanAPIService {
    @GET("api/taikhoan/user/{userId}")
    suspend fun getTaiKhoanNguoiDung(@Path("userId") userId: Int): BaseResponse<List<TaiKhoanModel>>

    @POST("api/taikhoan")
    suspend fun createTaiKhoan(@Body taikhoan: TaiKhoanModel) : Response<BaseResponseMes<TaiKhoanModel>>

    @PUT("api/taikhoan/{id}")
    suspend fun updateTaiKhoan(@Body taikhoan: TaiKhoanModel, @Path("id") id: Int) : Response<BaseResponseMes<TaiKhoanModel>>

    @POST("api/taikhoan/transfer")
    suspend fun chuyenTien(@Body chuyenTienRequest: ChuyenTienRequest): Response<StatusResponse>

    @DELETE("api/taikhoan/{id}")
    suspend fun deleteTaiKhoan(
        @Path("id") id: Int
    ): Response<StatusResponse>
}