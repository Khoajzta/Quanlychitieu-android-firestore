package com.example.quanlythuchi_android_firestore.ui.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.ChuyenTienRequest
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.domain.respository.TaiKhoanRepository
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaiKhoanViewModel @Inject constructor(
    private val repo: TaiKhoanRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<TaiKhoanModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<TaiKhoanModel>>> = _uiState

    // State để theo dõi quá trình tạo tài khoản
    private val _createTaiKhoanState = MutableStateFlow<UiState<BaseResponseMes<TaiKhoanModel>>>(UiState.Loading)
    val createTaiKhoanState: StateFlow<UiState<BaseResponseMes<TaiKhoanModel>>> = _createTaiKhoanState

    private val _updateTaiKhoanState = MutableStateFlow<UiState<BaseResponseMes<TaiKhoanModel>>>(UiState.Loading)
    val updateTaiKhoanState: StateFlow<UiState<BaseResponseMes<TaiKhoanModel>>> = _updateTaiKhoanState
    private val _deleteTaiKhoanState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val deleteKhoanState: StateFlow<UiState<StatusResponse>> = _deleteTaiKhoanState

    private val _chuyentienTaiKhoanState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val chuyentienTaiKhoanState: StateFlow<UiState<StatusResponse>> = _chuyentienTaiKhoanState

    fun loadTaiKhoans(userId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = repo.getTaiKhoanNguoiDung(userId)
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }
        }
    }

    fun createTaiKhoan(taiKhoan: TaiKhoanModel) {
        viewModelScope.launch {
            _createTaiKhoanState.value = UiState.Loading
            try {
                val response = repo.createaTaiKhoan(taiKhoan)
                _createTaiKhoanState.value = UiState.Success(response)
                // Sau khi tạo thành công, reload danh sách
                loadTaiKhoans(taiKhoan.id_nguoidung)
            } catch (e: Exception) {
                _createTaiKhoanState.value = UiState.Error(e.localizedMessage ?: "Không thể tạo tài khoản")
            }
        }
    }

    fun updateTaiKhoan(taiKhoan: TaiKhoanModel, id: Int) {
        viewModelScope.launch {
            _updateTaiKhoanState.value = UiState.Loading
            try {
                val response = repo.updateTaiKhoan(taiKhoan, id)
                _updateTaiKhoanState.value = UiState.Success(response)
                // Reload danh sách sau khi update thành công
                loadTaiKhoans(taiKhoan.id_nguoidung)
            } catch (e: Exception) {
                _updateTaiKhoanState.value = UiState.Error(e.localizedMessage ?: "Không thể update")
            }
        }
    }

    fun chuyenTien(chuyenTienRequest: ChuyenTienRequest){
        viewModelScope.launch {
            _chuyentienTaiKhoanState.value = UiState.Loading
            try {
                val response = repo.chuyenTien(chuyenTienRequest)
                _chuyentienTaiKhoanState.value = UiState.Success(response)
            }catch (e: Exception){
                _chuyentienTaiKhoanState.value = UiState.Error(e.localizedMessage ?: "Không thể chuyển tiền")
            }
        }
    }

    fun deleteTaiKhoan(id:Int){
        viewModelScope.launch {
            _deleteTaiKhoanState.value = UiState.Loading
            try {
                val result = repo.deleteTaiKhoan(id)
                if (result.success) {
                    _deleteTaiKhoanState.value = UiState.Success(result)
                } else {
                    _deleteTaiKhoanState.value = UiState.Error(result.message)
                }
            } catch (e: Exception) {
                _deleteTaiKhoanState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

}
