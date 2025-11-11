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

    private val _getAllTaiKhoanState = MutableStateFlow<UiState<List<TaiKhoanModel>>>(UiState.Loading)
    val getAlltaikhoanState: StateFlow<UiState<List<TaiKhoanModel>>> = _getAllTaiKhoanState

    private val _getTaiKhoanChinhState = MutableStateFlow<UiState<TaiKhoanModel>>(UiState.Loading)
    val gettaikhoanChinhState: StateFlow<UiState<TaiKhoanModel>> = _getTaiKhoanChinhState

    private val _createTaiKhoanState = MutableStateFlow<UiState<BaseResponseMes<TaiKhoanModel>>>(UiState.Loading)
    val createTaiKhoanState: StateFlow<UiState<BaseResponseMes<TaiKhoanModel>>> = _createTaiKhoanState

    private val _updateTaiKhoanState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val updateTaiKhoanState: StateFlow<UiState<StatusResponse>> = _updateTaiKhoanState
    private val _deleteTaiKhoanState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val deleteKhoanState: StateFlow<UiState<StatusResponse>> = _deleteTaiKhoanState

    private val _chuyentienTaiKhoanState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val chuyentienTaiKhoanState: StateFlow<UiState<StatusResponse>> = _chuyentienTaiKhoanState

    fun getAllTaiKhoanByUser(userId: String) {
        viewModelScope.launch {
            _getAllTaiKhoanState.value = UiState.Loading
            try {
                val result = repo.getTaiKhoanNguoiDung(userId)
                _getAllTaiKhoanState.value = UiState.Success(result)
            } catch (e: Exception) {
                _getAllTaiKhoanState.value = UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }
        }
    }

    fun getTaiKhoanChinh(userId: String) {
        viewModelScope.launch {
            _getTaiKhoanChinhState.value = UiState.Loading
            try {
                val result = repo.getTaiKhoanChinhNguoiDung(userId)
                _getTaiKhoanChinhState.value = UiState.Success(result)
            }catch (e: Exception){
                _getTaiKhoanChinhState.value = UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }

        }
    }


    fun createTaiKhoan(taiKhoan: TaiKhoanModel) {
        viewModelScope.launch {
            _createTaiKhoanState.value = UiState.Loading
            try {
                val response = repo.createTaiKhoan(taiKhoan)
                _createTaiKhoanState.value = UiState.Success(response)
                // Sau khi tạo thành công, reload danh sách
                getAllTaiKhoanByUser(taiKhoan.id_nguoidung)
            } catch (e: Exception) {
                _createTaiKhoanState.value = UiState.Error(e.localizedMessage ?: "Không thể tạo tài khoản")
            }
        }
    }

    fun resetCreateState() {
        _createTaiKhoanState.value = UiState.Loading
    }


    fun updateTaiKhoan(taiKhoan: TaiKhoanModel) {
        viewModelScope.launch {
            _updateTaiKhoanState.value = UiState.Loading
            try {
                val response = repo.updateTaiKhoan(taiKhoan)
                _updateTaiKhoanState.value = UiState.Success(response)
                // Reload danh sách sau khi update thành công
                getAllTaiKhoanByUser(taiKhoan.id_nguoidung)
            } catch (e: Exception) {
                _updateTaiKhoanState.value = UiState.Error(e.localizedMessage ?: "Không thể update")
            }
        }
    }

    fun resetUpdateState() {
        _updateTaiKhoanState.value = UiState.Loading
    }

    fun deleteTaiKhoan(id: String){
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

    fun resetDeleteState() {
        _deleteTaiKhoanState.value = UiState.Loading
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



}
