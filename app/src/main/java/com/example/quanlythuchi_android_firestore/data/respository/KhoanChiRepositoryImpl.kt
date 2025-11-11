package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.Utils.getStartAndEndOfMonth
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.domain.respository.KhoanChiRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class KhoanChiRepositoryImpl @Inject constructor(
) : KhoanChiRepository {

    private val db = Firebase.firestore
    private val collection = db.collection("khoanchi")

    override suspend fun getKhoanChi(userId: String): List<KhoanChiModel> {
        return try {
            val querySnapshot = collection
                .whereEqualTo("id_nguoidung", userId)
                .get()
                .await()

            querySnapshot.documents.mapNotNull { document ->
                document.toObject(KhoanChiModel::class.java)?.copy(
                    id = document.id
                )
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi lấy danh sách khoản chi: ${e.message}")
            emptyList()
        }
    }


    override suspend fun getKhoanChiTheoThangVaNam(
        userId: String,
        thang: Int,
        nam: Int
    ): List<KhoanChiModel> {
        return try {
            val db = FirebaseFirestore.getInstance()
            val khoanChiCollection = db.collection("khoanchi")
            val chiTieuCollection = db.collection("chitieu")

            val (startOfMonthStr, endOfMonthStr) = getStartAndEndOfMonth(thang, nam)

            // Lấy danh sách khoản chi của user trong tháng đó
            val khoanChiSnapshot = khoanChiCollection
                .whereEqualTo("id_nguoidung", userId)
                .whereGreaterThanOrEqualTo("ngay_batdau", startOfMonthStr)
                .whereLessThanOrEqualTo("ngay_ketthuc", endOfMonthStr)
                .get()
                .await()

            Log.d(
                "Firestore",
                "✅ Tìm thấy ${khoanChiSnapshot.size()} khoản chi trong tháng $thang/$nam"
            )

            // Duyệt từng khoản chi để tính tổng tiền & số lượng chi tiêu
            khoanChiSnapshot.documents.mapNotNull { doc ->
                val khoanChi = doc.toObject(KhoanChiModel::class.java)?.copy(id = doc.id)
                if (khoanChi != null) {
                    // Lấy các chi tiêu thuộc khoản chi này
                    val chiTieuSnapshot = chiTieuCollection
                        .whereEqualTo("id_khoanchi", khoanChi.id)
                        .get()
                        .await()

                    // Lọc chi tiêu trong đúng tháng/năm yêu cầu
                    val chiTieuTrongThang = chiTieuSnapshot.documents.filter { chiTieuDoc ->
                        val ngayTaoStr = chiTieuDoc.getString("ngay_tao") ?: return@filter false
                        val (namChi, thangChi) = ngayTaoStr.split("-").map { it.toInt() }.let {
                            it[0] to it[1]
                        }
                        namChi == nam && thangChi == thang
                    }

                    val tongTien = chiTieuTrongThang.sumOf {
                        val soTien = it.get("so_tien")
                        when (soTien) {
                            is Long -> soTien
                            is Double -> soTien.toLong()
                            else -> 0L
                        }
                    }

                    val soLuong = chiTieuTrongThang.size

                    Log.d(
                        "Firestore",
                        "📊 Khoản chi '${khoanChi.ten_khoanchi}' có $soLuong chi tiêu, tổng $tongTien đ"
                    )

                    khoanChi.copy(
                        so_luong_chi_tieu = soLuong,
                        tong_tien_da_chi = tongTien
                    )
                } else null
            }
        } catch (e: Exception) {
            Log.e("FirestoreQuery", "❌ Lỗi truy vấn: ${e.message}", e)
            emptyList()
        }
    }





    override suspend fun getKhoanChiById(id_khoanchi: String): KhoanChiModel {
        return try {
            val querySnapshot = collection
                .whereEqualTo("id", id_khoanchi)
                .get()
                .await()

            val document = querySnapshot.documents.firstOrNull()
                ?: throw Exception("Không tìm thấy khoản chi có id: $id_khoanchi")

            document.toObject(KhoanChiModel::class.java)
                ?: throw Exception("Lỗi khi chuyển dữ liệu sang model")
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi lấy khoản chi theo id: ${e.message}")
            throw e
        }
    }


    override suspend fun createKhoanChi(khoanchi: KhoanChiModel): StatusResponse {
        return try {
            val docRef = collection.document()
            val data = hashMapOf(
                "id" to docRef.id,
                "ten_khoanchi" to khoanchi.ten_khoanchi,
                "id_nguoidung" to khoanchi.id_nguoidung,
                "so_tien_du_kien" to khoanchi.so_tien_du_kien,
                "ngay_batdau" to khoanchi.ngay_batdau,
                "ngay_ketthuc" to khoanchi.ngay_ketthuc,
                "mausac" to khoanchi.mausac,
                "emoji" to khoanchi.emoji,
            )
            docRef.set(data).await()
            Log.d("Firestore", "Tạo khoản chi thành công với id: ${docRef.id}")
            StatusResponse(
                success = true,
                message = "Tạo khoản chi thành công",
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi tạo khoản chi: ${e.message}", e)
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định"
            )
        }
    }

    override suspend fun updateKhoanChi(
        khoanchi: KhoanChiModel
    ): StatusResponse {
        return try {
            if (khoanchi.id.isNullOrBlank()) {
                return StatusResponse(
                    success = false,
                    message = "ID khoản chi không được rỗng"
                )
            }

            val data = hashMapOf<String, Any?>(
                "ten_khoanchi" to khoanchi.ten_khoanchi,
                "id_nguoidung" to khoanchi.id_nguoidung,
                "so_tien_du_kien" to khoanchi.so_tien_du_kien,
                "ngay_batdau" to khoanchi.ngay_batdau,
                "ngay_ketthuc" to khoanchi.ngay_ketthuc,
                "mausac" to khoanchi.mausac,
                "emoji" to khoanchi.emoji,
                "so_luong_chi_tieu" to khoanchi.so_luong_chi_tieu,
                "tong_tien_da_chi" to khoanchi.tong_tien_da_chi
            )

            // Cập nhật document theo id
            collection.document(khoanchi.id).update(data).await()

            Log.d("Firestore", "Đã cập nhật khoản chi có id: ${khoanchi.id}")
            StatusResponse(
                success = true,
                message = "Cập nhật khoản chi thành công"
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi cập nhật khoản chi: ${e.message}", e)
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định khi cập nhật khoản chi"
            )
        }
    }


    override suspend fun deleteKhoanChi(id_khoanchi: String): StatusResponse {
        return try {
            val querySnapshot = collection
                .whereEqualTo("id", id_khoanchi)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                document.reference.delete().await()
                Log.d("Firestore", "Đã xóa khoản chi có id: $id_khoanchi (docId=${document.id})")
            }

            StatusResponse(
                success = true,
                message = "Xóa khoản chi thành công"
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi xóa ckhoản chi: ${e.message}", e)
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định khi xóa khoản chi"
            )
        }
    }
}