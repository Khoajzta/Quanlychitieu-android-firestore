package com.example.quanlythuchi_android_firestore.data.remote.dto

data class CheckEmailResponse<T> (
    val success: Boolean,
    val exists : Boolean,
    val data: T,
    val message: String
)
