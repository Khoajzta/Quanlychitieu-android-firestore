package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.Utils.getStartAndEndOfMonth
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeThuNhapModel
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel
import com.example.quanlythuchi_android_firestore.domain.respository.ThuNhapRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ThuNhapRepositoryImpl @Inject constructor(
) : ThuNhapRepository {
    private val db = Firebase.firestore
    private val collection = db.collection("thunhap")

    override suspend fun getThuNhapTheoThangVaNam(
        userId: String,
        thang: Int,
        nam: Int
    ): List<ThuNhapModel> {
        return try {
            val (startOfMonthStr, endOfMonthStr) = getStartAndEndOfMonth(thang, nam)

            val querySnapshot = collection
                .whereEqualTo("id_nguoidung", userId)
                .whereGreaterThanOrEqualTo("ngay_tao", startOfMonthStr)
                .whereLessThanOrEqualTo("ngay_tao", endOfMonthStr)
                .get()
                .await()

            Log.d(
                "danh sách thu nhập tháng $thang/$nam của user $userId",
                "✅ Tìm thấy ${querySnapshot.size()} documents trong tháng $thang/$nam"
            )

            querySnapshot.documents.mapNotNull { doc ->
                doc.toObject(ThuNhapModel::class.java)
            }

        } catch (e: Exception) {
            Log.e("FirestoreQuery", "❌ Lỗi truy vấn: ${e.message}", e)
            emptyList()
        }
    }


    override suspend fun thongkeTheoNam(
        userId: String,
        nam: Int
    ): List<ThongKeThuNhapModel> {
        return try {
            val querySnapshot = collection
                .whereEqualTo("id_nguoidung", userId)
                .get()
                .await()

            val tongTheoThang = mutableMapOf<Int, Long>()

            for (doc in querySnapshot.documents) {
                val ngayTao = doc.getString("ngay_tao") ?: continue
                val soTien = doc.getLong("so_tien") ?: 0L

                // ✅ Tách năm và tháng từ chuỗi "yyyy-MM-dd"
                val parts = ngayTao.split("-")
                if (parts.size >= 2) {
                    val namItem = parts[0].toIntOrNull()
                    val thangItem = parts[1].toIntOrNull()

                    if (namItem == nam && thangItem != null) {
                        tongTheoThang[thangItem] = (tongTheoThang[thangItem] ?: 0L) + soTien
                    }
                }
            }

            // ✅ Trả về danh sách 12 tháng
            (1..12).map { thang ->
                ThongKeThuNhapModel(
                    thang = thang,
                    tongThu = tongTheoThang[thang] ?: 0L
                )
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi thống kê theo năm: ${e.message}")
            (1..12).map { thang ->
                ThongKeThuNhapModel(thang = thang, tongThu = 0L)
            }
        }
    }



    override suspend fun createThuNhap(thuNhapModel: ThuNhapModel): StatusResponse {
        return try {
            val docRef = collection.document()
            val data = hashMapOf(
                "id" to docRef.id,
                "id_nguoidung" to thuNhapModel.id_nguoidung,
                "id_taikhoan" to thuNhapModel.id_taikhoan,
                "so_tien" to thuNhapModel.so_tien,
                "ngay_tao" to thuNhapModel.ngay_tao,
                "ghi_chu" to thuNhapModel.ghi_chu,
            )
            docRef.set(data).await()
            Log.d("Firestore", "Tạo thu nhập thành công với id: ${docRef.id}")
            StatusResponse(
                success = true,
                message = "Tạo thu nhập thành công",
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi tạo thu nhập: ${e.message}", e)
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định"
            )
        }
    }

    override suspend fun deleteThuNhap(id: String): StatusResponse {
        return try {
            val querySnapshot = collection
                .whereEqualTo("id", id)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                document.reference.delete().await()
                Log.d("Firestore", "Đã xóa thu nhập có id: $id (docId=${document.id})")
            }

            StatusResponse(
                success = true,
                message = "Xóa thu nhập thành công"
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi xóa thu nhập ${e.message}", e)
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định khi xóa thu nhập"
            )
        }
    }
}