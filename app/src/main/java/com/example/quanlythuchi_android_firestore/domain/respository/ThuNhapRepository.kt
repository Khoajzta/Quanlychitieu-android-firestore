package com.example.quanlythuchi_android_firestore.domain.respository

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeThuNhapModel
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel

interface ThuNhapRepository {
    suspend fun getThuNhapTheoThangVaNam(userId: String, thang:Int, nam:Int) : List<ThuNhapModel>
    suspend fun thongkeTheoNam(userId: String, nam:Int) : List<ThongKeThuNhapModel>
    suspend fun createThuNhap(thuNhapModel: ThuNhapModel): StatusResponse
    suspend fun deleteThuNhap(id: String):StatusResponse
}