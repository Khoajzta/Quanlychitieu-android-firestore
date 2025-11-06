package com.example.quanlythuchi_android_firestore.domain.respository

import com.example.quanlythuchi_android_firestore.domain.model.ChuyenTienModel

interface ChuyenTienRepository {
    suspend fun getLichSuChuyenTienByUser(userId: Int): List<ChuyenTienModel>
}
