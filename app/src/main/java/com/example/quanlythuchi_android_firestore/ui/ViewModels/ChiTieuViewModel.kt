package com.example.quanlythuchi_android_firestore.ui.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.respository.ChiTieuRepository
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChiTieuViewModel @Inject constructor(
    private val repository: ChiTieuRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ChiTieuModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ChiTieuModel>>> = _uiState

    private val _thangVaNamuiState = MutableStateFlow<UiState<List<ChiTieuModel>>>(UiState.Loading)
    val thangVaNam: StateFlow<UiState<List<ChiTieuModel>>> = _thangVaNamuiState

    private val _thongKeState = MutableStateFlow<UiState<List<ThongKeChiTieuModel>>>(UiState.Loading)
    val thongKeState: StateFlow<UiState<List<ThongKeChiTieuModel>>> = _thongKeState

    private val _createState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val createState: StateFlow<UiState<StatusResponse>> = _createState

    private val _updateState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val updateState: StateFlow<UiState<StatusResponse>> = _updateState

    private val _deleteState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val deleteState: StateFlow<UiState<StatusResponse>> = _deleteState


    // 🟢 Thêm chi tiêu
    fun createChiTieu(chitieu: ChiTieuModel) {
        viewModelScope.launch {
            _createState.value = UiState.Loading
            try {
                val response = repository.createChiTieu(chitieu)
                if (response.success) {
                    _createState.value = UiState.Success(response)
                    Log.d("ChiTieuViewModel create", "Tạo chi tiêu thành công")
                } else {
                    _createState.value = UiState.Error(response.message ?: "Không thể tạo chi tiêu")
                }
            } catch (e: Exception) {
                Log.e("ChiTieuViewModel", "Lỗi khi tạo chi tiêu: ${e.message}", e)
                _createState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }


    fun updateChiTieu(chitieu: ChiTieuModel) {
        viewModelScope.launch {
            _updateState.value = UiState.Loading
            try {
                val response: StatusResponse = repository.updateChiTieu(chitieu)
                if (response.success) {
                    _updateState.value = UiState.Success(response)

                    Log.d("ChiTieuViewModel update", "Cập nhật chi tiêu thành công id=${chitieu.id}")
                } else {
                    _updateState.value = UiState.Error(response.message ?: "Không thể cập nhật chi tiêu")
                }
            } catch (e: Exception) {
                Log.e("ChiTieuViewModel", "Lỗi updateChiTieu: ${e.message}", e)
                _updateState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    // 🔴 Xóa chi tiêu
    fun deleteChiTieu(id: String) {
        viewModelScope.launch {
            _deleteState.value = UiState.Loading
            try {
                val response: StatusResponse = repository.deleteChiTieu(id)
                if (response.success) {
                    _deleteState.value = UiState.Success(response)
                } else {
                    _deleteState.value = UiState.Error(response.message ?: "Không thể xóa chi tiêu")
                }
            } catch (e: Exception) {
                Log.e("ChiTieuViewModel", "Lỗi deleteChiTieu: ${e.message}", e)
                _deleteState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }


    // 🟡 Lấy chi tiêu theo khoản chi và người dùng
    fun getChiTieuTheoKhoanChiCuaNguoiDung(idKhoanChi: Int, userId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val response = repository.getChiTieuTheoKhoanChiCuaNguoiDung(idKhoanChi, userId)
                if (response.success) {
                    _uiState.value = UiState.Success(response.data ?: emptyList())
                } else {
                    _uiState.value = UiState.Error("Không thể tải dữ liệu")
                }
            } catch (e: Exception) {
                Log.e("ChiTieuViewModel", "Lỗi khi lấy dữ liệu: ${e.message}", e)
                _uiState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }


    // 🔵 Lấy chi tiêu theo tháng và năm
    fun getChiTieuTheoThangVaNam(userId: String, thang: Int, nam: Int) {
        viewModelScope.launch {
            _thangVaNamuiState.value = UiState.Loading
            try {
                val response = repository.getChiTieuTheoThangVaNam(userId, thang, nam)
                if (response.isNotEmpty()) {
                    _thangVaNamuiState.value = UiState.Success(response)
                } else {
                    _thangVaNamuiState.value = UiState.Success(emptyList()) // để không crash
                }
            } catch (e: Exception) {
                Log.e("ChiTieuViewModel", "Lỗi getChiTieuTheoThangVaNam: ${e.message}", e)
                _thangVaNamuiState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }






    // 🟣 Thống kê theo năm
    fun thongKeTheoNam(userId: String, nam: Int) {
        viewModelScope.launch {
            _thongKeState.value = UiState.Loading
            try {
                val response: BaseResponseMes<List<ThongKeChiTieuModel>> =
                    repository.thongKeTheoNam(userId, nam)

                Log.d("ChiTieuViewModel", "Thống kê theo năm: $response")
                if (response.success) {
                    _thongKeState.value = UiState.Success(response.data ?: emptyList())
                    Log.d("ChiTieuViewModel", "Thống kê theo năm thành công")
                } else {
                    _thongKeState.value = UiState.Error(response.message ?: "Không thể thống kê")
                }
            } catch (e: Exception) {
                Log.e("ChiTieuViewModel", "Lỗi thongKeTheoNam: ${e.message}", e)
                _thongKeState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }
}