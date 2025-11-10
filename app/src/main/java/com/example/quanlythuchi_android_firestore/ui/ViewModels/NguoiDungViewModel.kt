package com.example.quanlythuchi_android_firestore.ui.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi_android_firestore.Utils.formatDateToDB
import com.example.quanlythuchi_android_firestore.Utils.formatMillisToDB
import com.example.quanlythuchi_android_firestore.data.local.DataStoreManager
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.CheckEmailResponse
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.domain.model.NguoiDungModel
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.domain.respository.AuthRepository
import com.example.quanlythuchi_android_firestore.domain.respository.KhoanChiRepository
import com.example.quanlythuchi_android_firestore.domain.respository.NguoiDungRepository
import com.example.quanlythuchi_android_firestore.domain.respository.TaiKhoanRepository
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class NguoiDungViewModel @Inject constructor(
    private val repository: NguoiDungRepository,
    private val authRepository: AuthRepository,
    private val dataStoreManager: DataStoreManager,
    private val taikhoanRepository: TaiKhoanRepository,
    private val khoanChiRepository: KhoanChiRepository
) : ViewModel() {

    var createNguoiDungState by mutableStateOf<UiState<BaseResponse<NguoiDungModel>>>(UiState.Loading)
        private set

    var loginState by mutableStateOf<UiState<NguoiDungModel>>(UiState.Loading)
        private set

    var checkEmailState by mutableStateOf<UiState<CheckEmailResponse<NguoiDungModel>>>(UiState.Loading)
        private set

    private val _getByIdState = MutableStateFlow<UiState<NguoiDungModel>>(UiState.Loading)
    val getByIdState: StateFlow<UiState<NguoiDungModel>> = _getByIdState

    // 🌀 Trạng thái loading toàn cục
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    // 👉 Đăng nhập với Google
    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                setLoading(true) // bật loading khi bắt đầu
                loginState = UiState.Loading

                val result = authRepository.signInWithGoogle(idToken)
                loginState = result

                if (result is UiState.Success) {
                    val user = result.data
                    // 👉 Lưu user_id và email sau khi đăng nhập thành công
                    dataStoreManager.saveUserId(user.id!!)

                    Log.d(
                        "LOGIN_SUCCESS",
                        "Tên: ${result.data.ten}, Email: ${result.data.email}, Token: ${result.data.token}"
                    )

                    saveUserId(user.id)
                }
            } catch (e: Exception) {
                loginState = UiState.Error(e.message ?: "Lỗi đăng nhập Google")
                Log.e("LOGIN_ERROR", e.message ?: "Unknown error")
            } finally {
                setLoading(false) // tắt loading dù thành công hay thất bại
            }
        }
    }

    fun getUserId() = dataStoreManager.getUserId()
    fun isFirstLaunch() = dataStoreManager.isFirstLaunch()

    fun setFirstLaunch(isFirst: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setFirstLaunch(isFirst)
        }
    }

    fun saveUserId(userId: String) {
        viewModelScope.launch {
            dataStoreManager.saveUserId(userId)
            Log.d("SAVE_USER_ID", "Đã lưu userId = $userId")
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearUserId()
        }
    }

    fun createNguoiDung(nguoiDung: NguoiDungModel) {
        viewModelScope.launch {
            createNguoiDungState = UiState.Loading
            try {
                val result = repository.createNguoiDung(nguoiDung)
                createNguoiDungState = UiState.Success(result)
            } catch (e: Exception) {
                createNguoiDungState = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun handleLoginAndCheckUser(
        nguoiDung: NguoiDungModel,
        onSuccess: (String) -> Unit, // ✅ đổi kiểu callback để nhận id
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            setLoading(true)
            try {
                val checkResult = repository.checkEmailNguoidung(nguoiDung.email!!)

                if (checkResult.exists) {
                    val id = checkResult.data!!.id
                    saveUserId(id!!)
                    onSuccess(id)
                } else {
                    val newUser = repository.createNguoiDung(nguoiDung)
                    val id = newUser.data.id
                    saveUserId(id!!)

                    val taiKhoanchinh = TaiKhoanModel(
                        id = "",
                        id_nguoidung = id,
                        ten_taikhoan = "Tài khoản chính",
                        so_du = 0L,
                        loai_taikhoan = 1,
                        mo_ta = "Tài khoản chi tiêu"
                    )

                    taikhoanRepository.createTaiKhoan(taiKhoanchinh)

                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val now = LocalDate.now()
                    val firstDay = now.withDayOfMonth(1).format(formatter)
                    val lastDay = now.withDayOfMonth(now.lengthOfMonth()).format(formatter)

                    val khoanChiMacDinh = listOf(
                        KhoanChiModel("", "Tiền ăn", id, 3_000_000L, firstDay, lastDay, "yellow", "🍕"),
                        KhoanChiModel("", "Du lịch", id, 5_000_000L, firstDay, lastDay, "green", "✈️"),
                        KhoanChiModel("", "Giải trí", id, 1_000_000L, firstDay, lastDay, "blue", "🎵")
                    )

                    khoanChiMacDinh.forEach { khoanChiRepository.createKhoanChi(it) }
                    onSuccess(id)
                }
            } catch (e: Exception) {
                onError(e.message ?: "Lỗi không xác định")
            } finally {
                setLoading(false)
            }
        }
    }


    fun checkEmailNguoiDung(email: String) {
        viewModelScope.launch {
            checkEmailState = UiState.Loading
            try {
                val result = repository.checkEmailNguoidung(email)
                checkEmailState = UiState.Success(result)
                Log.d("CHECK_EMAIL", "Email $email tồn tại: ${result.exists}")
            } catch (e: Exception) {
                checkEmailState = UiState.Error(e.message ?: "Unknown error")
                Log.e("CHECK_EMAIL_ERROR", e.message ?: "Unknown error")
            }
        }
    }

    fun getNguoiDungByID(id : String){
        viewModelScope.launch {
            _getByIdState.value = UiState.Loading
            try {
                var result =repository.getNguoiDungByID(id)
                _getByIdState.value = UiState.Success(result)
                Log.d("GET_BY_ID", "Email ${result}")
            }catch (e: Exception){
                _getByIdState.value = UiState.Error(message = e.message?: "Unknown error")
                Log.e("getbyId error", e.message ?: "Unknown error")
            }
        }
    }
}
