package com.example.quanlythuchi_android_firestore.domain.respository

import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.CheckEmailResponse
import com.example.quanlythuchi_android_firestore.domain.model.NguoiDungModel

interface NguoiDungRepository {
    suspend fun createNguoiDung(nguoidung: NguoiDungModel) : BaseResponse<NguoiDungModel>
    suspend fun checkEmailNguoidung(email :String): CheckEmailResponse<NguoiDungModel>
    suspend fun getNguoiDungByID(id: String) : NguoiDungModel
}