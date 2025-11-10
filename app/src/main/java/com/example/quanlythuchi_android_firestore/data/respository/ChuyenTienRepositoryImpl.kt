package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.domain.model.ChuyenTienModel
import com.example.quanlythuchi_android_firestore.domain.respository.ChuyenTienRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class ChuyenTienRepositoryImpl @Inject constructor(
): ChuyenTienRepository {

    private val db = Firebase.firestore
    private val collection = db.collection("chuyentien")

    override suspend fun getLichSuChuyenTienByUser(userId: String): List<ChuyenTienModel> {
        return try {

            // Query tất cả lịch sử liên quan đến userId
            val querySnapshot = collection
                .whereEqualTo("id_nguoidung", userId)
                .orderBy("ngay_chuyen", Query.Direction.DESCENDING) // mới nhất trước
                .get()
                .await()

            Log.d("Firestore", "✅ Tìm thấy ${querySnapshot.documents.size} lịch sử chuyển tiền")

            querySnapshot.documents.mapNotNull { doc ->
                doc.toObject(ChuyenTienModel::class.java)
            }

        } catch (e: Exception) {
            Log.e("Firestore", "❌ Lỗi khi lấy lịch sử chuyển tiền: ${e.message}", e)
            emptyList()
        }
    }

}