package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.Utils.getStartAndEndOfMonth
import com.example.quanlythuchi_android_firestore.data.remote.ChiTieuAPIService
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.respository.ChiTieuRepository
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class ChiTieuRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ChiTieuRepository {

    private val db = Firebase.firestore
    private val collection = db.collection("chitieu")


    override suspend fun getChiTieuTheoKhoanChiCuaNguoiDung(
        id_khoanchi: Int,
        userId: String
    ): BaseResponse<List<ChiTieuModel>> {
        return try {
            val result = collection
                .whereEqualTo("id_khoanchi", id_khoanchi)
                .whereEqualTo("id_nguoidung", userId)
                .get()
                .await()

            Log.d("FirestoreQuery", "Tìm thấy ${result.size()} document")
            for (doc in result.documents) {
                Log.d("FirestoreQuery", "Document: ${doc.data}")
            }

            BaseResponse(
                success = true,
                data = result.toObjects(ChiTieuModel::class.java)
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi Firestore: ${e.message}", e)
            BaseResponse(
                success = false,
                data = emptyList()
            )
        }
    }

    override suspend fun getChiTieuTheoThangVaNam(
        userId: String,
        thang: Int,
        nam: Int
    ): List<ChiTieuModel> {
        return try {
            val (startOfMonthStr, endOfMonthStr) = getStartAndEndOfMonth(thang, nam)

            val querySnapshot = collection
                .whereEqualTo("id_nguoidung", userId)
                .whereGreaterThanOrEqualTo("ngay_tao", startOfMonthStr)
                .whereLessThanOrEqualTo("ngay_tao", endOfMonthStr)
                .get()
                .await()

            Log.d("danh sách chi tiêu tháng $thang/$nam của user $userId", "✅ Tìm thấy ${querySnapshot.size()} documents trong tháng $thang/$nam")

            querySnapshot.documents.mapNotNull { doc ->
                doc.toObject(ChiTieuModel::class.java)
            }



        } catch (e: Exception) {
            Log.e("FirestoreQuery", "❌ Lỗi truy vấn: ${e.message}", e)
            emptyList()
        }
    }

    override suspend fun createChiTieu(chitieu: ChiTieuModel): StatusResponse {
        return try {
            val docRef = collection.document()
            val data = hashMapOf(
                "id" to docRef.id,
                "id_nguoidung" to chitieu.id_nguoidung,
                "id_khoanchi" to chitieu.id_khoanchi,
                "id_taikhoan" to chitieu.id_taikhoan,
                "so_tien" to chitieu.so_tien,
                "ngay_tao" to chitieu.ngay_tao,
                "ghi_chu" to chitieu.ghi_chu
            )
            docRef.set(data).await()
            Log.d("Firestore", "Tạo chi tiêu thành công với id: ${docRef.id}")
            StatusResponse(
                success = true,
                message = "Tạo chi tiêu thành công",
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi tạo chi tiêu: ${e.message}", e)
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định"
            )
        }
    }

    override suspend fun updateChiTieu(chitieu: ChiTieuModel): StatusResponse {
        return try {
            if (chitieu.id!!.isBlank()) {
                return StatusResponse(
                    success = false,
                    message = "ID chi tiêu không được rỗng"
                )
            }

            val data = hashMapOf(
                "id_nguoidung" to chitieu.id_nguoidung,
                "id_khoanchi" to chitieu.id_khoanchi,
                "id_taikhoan" to chitieu.id_taikhoan,
                "so_tien" to chitieu.so_tien,
                "ngay_tao" to chitieu.ngay_tao,
                "ghi_chu" to chitieu.ghi_chu
            )

            // Cập nhật document theo id
            collection.document(chitieu.id!!).update(data as Map<String, Any>).await()

            Log.d("Firestore", "Đã cập nhật chi tiêu có id: ${chitieu.id}")
            StatusResponse(
                success = true,
                message = "Cập nhật chi tiêu thành công"
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi cập nhật chi tiêu: ${e.message}", e)
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định khi cập nhật chi tiêu"
            )
        }
    }


    override suspend fun deleteChiTieu(id: String): StatusResponse {
        return try {
            val querySnapshot = collection
                .whereEqualTo("id", id)
                .get()
                .await()

            for (document in querySnapshot.documents) {
                document.reference.delete().await()
                Log.d("Firestore", "Đã xóa chi tiêu có id: $id (docId=${document.id})")
            }

            StatusResponse(
                success = true,
                message = "Xóa chi tiêu thành công"
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi xóa chi tiêu: ${e.message}", e)
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định khi xóa chi tiêu"
            )
        }
    }



    override suspend fun thongKeTheoNam(
        userId: String,
        nam: Int
    ): BaseResponseMes<List<ThongKeChiTieuModel>> {
        return try {
            // Lấy toàn bộ chi tiêu của người dùng
            val result = collection
                .whereEqualTo("id_nguoidung", userId)
                .get()
                .await()

            Log.d("FirestoreThongKe", "Tìm thấy ${result.size()} chi tiêu của user $userId")

            val listChiTieu = result.toObjects(ChiTieuModel::class.java)

            // Tạo danh sách 12 tháng mặc định có tổng chi = 0
            val thongKeList = (1..12).map { thang ->
                ThongKeChiTieuModel(thang = thang, tongChi = 0)
            }.toMutableList()

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            // Duyệt qua các chi tiêu để cộng dồn theo tháng và năm
            for (chiTieu in listChiTieu) {
                try {
                    val ngayTaoStr = chiTieu.ngay_tao
                    if (ngayTaoStr.isNullOrEmpty()) continue

                    val date = sdf.parse(ngayTaoStr) ?: continue
                    val cal = Calendar.getInstance()
                    cal.time = date

                    val chiNam = cal.get(Calendar.YEAR)
                    val chiThang = cal.get(Calendar.MONTH) + 1

                    if (chiNam == nam) {
                        val index = chiThang - 1
                        thongKeList[index] = thongKeList[index].copy(
                            tongChi = thongKeList[index].tongChi + (chiTieu.so_tien ?: 0)
                        )
                    }
                } catch (e: Exception) {
                    Log.e("FirestoreThongKe", "Lỗi khi xử lý ngày: ${e.message}")
                }
            }

            BaseResponseMes(
                success = true,
                data = thongKeList,
                message = "Lấy thống kê chi tiêu theo năm thành công"
            )
        } catch (e: Exception) {
            Log.e("FirestoreThongKe", "Lỗi Firestore: ${e.message}", e)
            BaseResponseMes(
                success = false,
                data = emptyList(),
                message = e.message ?: "Lỗi không xác định"
            )
        }
    }
}