package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.CheckEmailResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.domain.model.NguoiDungModel
import com.example.quanlythuchi_android_firestore.domain.respository.NguoiDungRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NguoiDungRepositoryImpl @Inject constructor(
) : NguoiDungRepository {

    private val db = Firebase.firestore
    private val collection = db.collection("nguoidung")

    override suspend fun createNguoiDung(
        nguoiDung: NguoiDungModel
    ): BaseResponse<NguoiDungModel> {
        return try {
            val docRef = collection.document() // Tạo document mới với ID tự sinh
            val id = docRef.id

            val data = hashMapOf(
                "id" to id,
                "ten" to nguoiDung.ten,
                "email" to nguoiDung.email,
                "url_avt" to nguoiDung.url_avt,
                "google_id" to nguoiDung.google_id,
                "token" to nguoiDung.token
            )

            docRef.set(data).await()

            // Trả về BaseResponse với object không null
            BaseResponse(
                success = true,
                data = nguoiDung.copy(id = id) // luôn là NguoiDungModel, không null
            )

        } catch (e: Exception) {
            BaseResponse(
                success = false,
                data = NguoiDungModel()
            )
        }
    }




    override suspend fun checkEmailNguoidung(
        email: String
    ): CheckEmailResponse<NguoiDungModel> {
        return try {
            val querySnapshot = collection
                .whereEqualTo("email", email)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                // Lấy document đầu tiên (nếu email đã tồn tại)
                val doc = querySnapshot.documents.first()
                val nguoiDung = doc.toObject(NguoiDungModel::class.java)

                CheckEmailResponse(
                    success = true,
                    exists = true,
                    data = nguoiDung, // có thể null nếu Firestore không parse được
                    message = "Email đã tồn tại"
                )
            } else {
                // Email chưa tồn tại
                CheckEmailResponse(
                    success = true,
                    exists = false,
                    data = null,
                    message = "Email chưa được đăng ký"
                )
            }

        } catch (e: Exception) {
            CheckEmailResponse(
                success = false,
                exists = false,
                data = null,
                message = "Firestore Error: ${e.message}"
            )
        }
    }



    override suspend fun getNguoiDungByID(id: String): NguoiDungModel {
        return try {
            val querySnapshot = collection
                .whereEqualTo("id", id)
                .get()
                .await()

            val document = querySnapshot.documents.firstOrNull()
                ?: throw Exception("Không tìm thấy khoản chi có id: $id")

            document.toObject(NguoiDungModel::class.java)
                ?: throw Exception("Lỗi khi chuyển dữ liệu sang model")
        } catch (e: Exception) {
            throw Exception("Lỗi khi lấy thông tin người dùng: ${e.message}")
        }
    }

}
