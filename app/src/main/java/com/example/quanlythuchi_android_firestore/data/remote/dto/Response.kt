package com.example.quanlythuchi_android_firestore.data.remote.dto

data class BaseResponse<T>(
    val success: Boolean,
    val data: T
)

data class BaseResponseMes<T>(
    val success: Boolean,
    val data: T?,
    val message: String
)

data class StatusResponse(
    val success: Boolean,
    val message: String
)