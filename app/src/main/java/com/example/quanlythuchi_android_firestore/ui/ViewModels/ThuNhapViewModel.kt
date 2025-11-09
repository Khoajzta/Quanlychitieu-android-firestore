package com.example.quanlythuchi_android_firestore.ui.ViewModels


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeThuNhapModel
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel
import com.example.quanlythuchi_android_firestore.domain.respository.ThuNhapRepository
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ThuNhapViewModel @Inject constructor(
    private val repository: ThuNhapRepository
) : ViewModel() {

    val ngaytruoc = LocalDate.now().minusMonths(1)

    private val _getByThangVaNamState = MutableStateFlow<UiState<List<ThuNhapModel>>>(UiState.Loading)
    val getByThangVaNamState: StateFlow<UiState<List<ThuNhapModel>>> = _getByThangVaNamState

    private val _getByThangTruocState = MutableStateFlow<UiState<List<ThuNhapModel>>>(UiState.Loading)
    val getByThangTruocState: StateFlow<UiState<List<ThuNhapModel>>> = _getByThangTruocState


    private val _thongKeTheoNamState = MutableStateFlow<UiState<List<ThongKeThuNhapModel>>>(UiState.Loading)
    val thongKeTheoNamState: StateFlow<UiState<List<ThongKeThuNhapModel>>> = _thongKeTheoNamState
    private val _createState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val createState: StateFlow<UiState<StatusResponse>> = _createState

    private val _deleteState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val deleteState: StateFlow<UiState<StatusResponse>> = _deleteState

    fun getThuNhapTheoThangVaNam(userId: String, thang: Int, nam: Int) {
        viewModelScope.launch {
            _getByThangVaNamState.value = UiState.Loading
            try {
                val result = repository.getThuNhapTheoThangVaNam(userId,thang,nam)
                _getByThangVaNamState.value = UiState.Success(result)
            } catch (e: Exception) {
                _getByThangVaNamState.value = UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }
        }
    }

    fun getThuNhapTheoThangTruoc(userId: String) {
        viewModelScope.launch {
            _getByThangTruocState.value = UiState.Loading
            try {
                val result = repository.getThuNhapTheoThangVaNam(userId,ngaytruoc.monthValue,ngaytruoc.year)
                _getByThangTruocState.value = UiState.Success(result)
            } catch (e: Exception) {
                _getByThangTruocState.value = UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }
        }
    }

    fun thongKeTheoNam(userId: String, nam: Int) {
        viewModelScope.launch {
            _thongKeTheoNamState.value = UiState.Loading
            try {
                val result = repository.thongkeTheoNam(userId, nam)
                _thongKeTheoNamState.value = UiState.Success(result)

            } catch (e:Exception) {
                _thongKeTheoNamState.value =
                    UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }
        }
    }
    fun createThuNhap(thunhap: ThuNhapModel) {
        viewModelScope.launch {
            _createState.value = UiState.Loading
            try {
                val response = repository.createThuNhap(thunhap)
                if (response.success) {
                    _createState.value = UiState.Success(response)
                } else {
                    _createState.value = UiState.Error(response.message ?: "Không thể tạo chi tiêu")
                }
            } catch (e: Exception) {
                _createState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun deleteThuNhap(id: String) {
        viewModelScope.launch {
            _deleteState.value = UiState.Loading
            try {
                val result = repository.deleteThuNhap(id)
                if (result.success) {
                    _deleteState.value = UiState.Success(result)
                } else {
                    _deleteState.value = UiState.Error(result.message)
                }
            } catch (e: Exception) {
                _deleteState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }
}

