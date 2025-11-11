package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.ChuyenTienRequest
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ChuyenTienModel
import com.example.quanlythuchi_android_firestore.domain.model.NguoiDungModel
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.domain.respository.TaiKhoanRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TaiKhoanRepositoryImpl@Inject constructor(
): TaiKhoanRepository {
    private val db = Firebase.firestore
    private val collection = db.collection("taikhoan")

    override suspend fun getTaiKhoanNguoiDung(userId: String): List<TaiKhoanModel> {
        return try {

            val querySnapshot = collection
                .whereEqualTo("id_nguoidung", userId)
                .get()
                .await()

            querySnapshot.documents.forEach { doc ->
                Log.d("Firestore", "➡️ Document ID: ${doc.id}, Data: ${doc.data}")
            }

            querySnapshot.documents.mapNotNull { document ->
                document.toObject(TaiKhoanModel::class.java)
            }

        } catch (e: Exception) {
            Log.e("Firestore", "❌ Lỗi khi lấy tài khoản của người dùng $userId: ${e.message}", e)
            throw Exception("Network/API Error: ${e.message}")
        }
    }

    override suspend fun getTaiKhoanChinhNguoiDung(userId: String): TaiKhoanModel {
        return try {
            val querySnapshot = collection
                .whereEqualTo("id_nguoidung", userId)
                .whereEqualTo("loai_taikhoan", 1)
                .get()
                .await()

            // Ghi log tất cả document lấy được
            querySnapshot.documents.forEach { doc ->
                Log.d("Firestore", "➡️ Document ID: ${doc.id}, Data: ${doc.data}")
            }

            // Lấy document đầu tiên nếu có
            val document = querySnapshot.documents.firstOrNull()
                ?: throw Exception("Không tìm thấy tài khoản chính của người dùng $userId")

            // Chuyển sang model
            document.toObject(TaiKhoanModel::class.java)
                ?: throw Exception("Lỗi chuyển đổi dữ liệu sang TaiKhoanModel")

        } catch (e: Exception) {
            Log.e("Firestore", "❌ Lỗi khi lấy tài khoản chính của người dùng $userId: ${e.message}", e)
            throw Exception("Network/API Error: ${e.message}")
        }
    }



    override suspend fun createTaiKhoan(taikhoan: TaiKhoanModel): BaseResponseMes<TaiKhoanModel> {
        return try {
            val docRef = collection.document()
            val id = docRef.id

            val data = hashMapOf(
                "id" to id,
                "id_nguoidung" to taikhoan.id_nguoidung,
                "ten_taikhoan" to taikhoan.ten_taikhoan,
                "so_du" to taikhoan.so_du,
                "loai_taikhoan" to taikhoan.loai_taikhoan,
                "mo_ta" to taikhoan.mo_ta
            )

            docRef.set(data).await()

            BaseResponseMes(
                success = true,
                message = "Tạo tài khoản thành công",
                data = taikhoan.copy(id = id)
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi tạo tài khoản: ${e.message}")
            BaseResponseMes(
                success = false,
                message = "Tạo tài khoản thất bại: ${e.message}",
                data = null
            )
        }
    }

    override suspend fun updateTaiKhoan(taikhoan: TaiKhoanModel): StatusResponse {
        return try {
            // Kiểm tra id
            if (taikhoan.id.isBlank()) {
                return StatusResponse(
                    success = false,
                    message = "ID tài khoản không được rỗng"
                )
            }

            // Dữ liệu cần cập nhật
            val data = hashMapOf(
                "id_nguoidung" to taikhoan.id_nguoidung,
                "ten_taikhoan" to taikhoan.ten_taikhoan,
                "so_du" to taikhoan.so_du,
                "loai_taikhoan" to taikhoan.loai_taikhoan,
                "mo_ta" to taikhoan.mo_ta
            )

            // Cập nhật document theo id
            collection.document(taikhoan.id).update(data as Map<String, Any>).await()

            Log.d("Firestore", "Đã cập nhật tài khoản có id: ${taikhoan.id}")
            StatusResponse(
                success = true,
                message = "Cập nhật tài khoản thành công"
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi cập nhật tài khoản: ${e.message}", e)
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định khi cập nhật tài khoản"
            )
        }
    }




    override suspend fun chuyenTien(chuyenTienRequest: ChuyenTienRequest): StatusResponse {
        return try {
            val taiKhoanNguoiGuiRef = collection.document(chuyenTienRequest.fromId)
            val taiKhoanNguoiNhanRef = collection.document(chuyenTienRequest.toId)
            val lichSuCollection = db.collection("chuyentien") // collection lưu lịch sử

            db.runTransaction { transaction ->
                // Lấy tài khoản người gửi và người nhận
                val nguoiGuiSnap = transaction.get(taiKhoanNguoiGuiRef)
                val nguoiNhanSnap = transaction.get(taiKhoanNguoiNhanRef)

                val nguoiGui = nguoiGuiSnap.toObject(TaiKhoanModel::class.java)
                    ?: throw Exception("Tài khoản người gửi không tồn tại")
                val nguoiNhan = nguoiNhanSnap.toObject(TaiKhoanModel::class.java)
                    ?: throw Exception("Tài khoản người nhận không tồn tại")

                // Kiểm tra số dư
                if (nguoiGui.so_du < chuyenTienRequest.amount) {
                    throw Exception("Số dư không đủ")
                }

                // Cập nhật số dư
                transaction.update(taiKhoanNguoiGuiRef, "so_du", nguoiGui.so_du - chuyenTienRequest.amount)
                transaction.update(taiKhoanNguoiNhanRef, "so_du", nguoiNhan.so_du + chuyenTienRequest.amount)

                // Tạo document lịch sử chuyển tiền
                val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                val idTuCap = "CT" + LocalDateTime.now().format(formatter)

                val newChuyenTienRef = lichSuCollection.document(idTuCap)
                val ngayHienTai = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))


                val chuyenTienModel = ChuyenTienModel(
                    id = idTuCap,
                    id_nguoidung = chuyenTienRequest.id_nguoidung,
                    id_taikhoan_nguon = chuyenTienRequest.fromId,
                    ten_taikhoan_nguon = nguoiGui.ten_taikhoan,
                    so_du_taikhoan_nguon = nguoiGui.so_du - chuyenTienRequest.amount,
                    id_taikhoan_dich = chuyenTienRequest.toId,
                    ten_taikhoan_dich = nguoiNhan.ten_taikhoan,
                    so_tien = chuyenTienRequest.amount,
                    ngay_chuyen = ngayHienTai,
                    ghi_chu = chuyenTienRequest.ghi_chu ?: ""
                )

                transaction.set(newChuyenTienRef, chuyenTienModel)
            }.await()

            Log.d("Firestore", "Chuyển tiền thành công và lưu lịch sử")
            StatusResponse(
                success = true,
                message = "Chuyển tiền thành công"
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi chuyển tiền: ${e.message}", e)
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định khi chuyển tiền"
            )
        }
    }



    override suspend fun deleteTaiKhoan(id: String): StatusResponse {
        return try {
            if (id.isBlank()) {
                return StatusResponse(
                    success = false,
                    message = "ID tài khoản không được rỗng"
                )
            }

            collection.document(id).delete().await()

            Log.d("Firestore", "Đã xóa tài khoản có id: $id")
            StatusResponse(
                success = true,
                message = "Xóa tài khoản thành công"
            )
        } catch (e: Exception) {
            Log.e("Firestore", "Lỗi khi xóa tài khoản: ${e.message}", e)
            StatusResponse(
                success = false,
                message = e.message ?: "Lỗi không xác định khi xóa tài khoản"
            )
        }
    }
}