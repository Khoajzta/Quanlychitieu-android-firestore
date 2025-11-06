package com.example.quanlythuchi_android_firestore.domain.respository

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeThuNhapModel
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel

interface ThuNhapRepository {
    suspend fun getThuNhapTheoThang(userId:Int, thang:Int, nam:Int) : BaseResponseMes<List<ThuNhapModel>>
    suspend fun thongkeTheoNam(userId:Int, nam:Int) : BaseResponseMes<List<ThongKeThuNhapModel>>
    suspend fun createThuNhap(thuNhapModel: ThuNhapModel): BaseResponse<ThuNhapModel>
    suspend fun deleteThuNhap(id:Int):StatusResponse
}