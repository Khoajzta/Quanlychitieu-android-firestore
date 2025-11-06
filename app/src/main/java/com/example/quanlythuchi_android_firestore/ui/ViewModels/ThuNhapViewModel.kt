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
import javax.inject.Inject

@HiltViewModel
class ThuNhapViewModel @Inject constructor(
    private val repository: ThuNhapRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ThuNhapModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ThuNhapModel>>> = _uiState

    private val _uiStateTheoThangTruoc = MutableStateFlow<UiState<List<ThuNhapModel>>>(UiState.Loading)
    val uiStateTheoThangTruoc: StateFlow<UiState<List<ThuNhapModel>>> = _uiStateTheoThangTruoc


    private val _thongKeTheoNamuiState = MutableStateFlow<UiState<List<ThongKeThuNhapModel>>>(UiState.Loading)
    val thongKeTheoNamState: StateFlow<UiState<List<ThongKeThuNhapModel>>> = _thongKeTheoNamuiState
    var thuNhapCreateState by mutableStateOf<UiState<BaseResponse<ThuNhapModel>>>(UiState.Loading)
        private set

    var deleteThuNhapState by mutableStateOf<UiState<StatusResponse>>(UiState.Loading)
        private set

    fun getThuNhapTheoThang(userId: Int, thang: Int, nam: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = repository.getThuNhapTheoThang(userId, thang, nam)
                if (result.success) {
                    _uiState.value = UiState.Success(result.data!!)
                    Log.d("THU_NHAP_VIEWMODEL", "Dữ liệu nhận được: ${result.data}")
                } else {
                    _uiState.value = UiState.Error(result.message ?: "Lỗi không xác định")
                    Log.e("THU_NHAP_VIEWMODEL", "Lỗi từ API: ${result.message}")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Lỗi kết nối")
                Log.e("THU_NHAP_VIEWMODEL", "Exception: ${e.message}")
            }
        }
    }

    fun getThuNhapTheoThangTruoc(userId: Int, thang: Int, nam: Int) {
        viewModelScope.launch {
            _uiStateTheoThangTruoc.value = UiState.Loading
            try {
                val result = repository.getThuNhapTheoThang(userId, thang, nam)
                if (result.success) {
                    _uiStateTheoThangTruoc.value = UiState.Success(result.data!!)
                    Log.d("THU_NHAP_VIEWMODEL", "Dữ liệu nhận được: ${result.data}")
                } else {
                    _uiStateTheoThangTruoc.value = UiState.Error(result.message ?: "Lỗi không xác định")
                    Log.e("THU_NHAP_VIEWMODEL", "Lỗi từ API: ${result.message}")
                }
            } catch (e: Exception) {
                _uiStateTheoThangTruoc.value = UiState.Error(e.message ?: "Lỗi kết nối")
                Log.e("THU_NHAP_VIEWMODEL", "Exception: ${e.message}")
            }
        }
    }

    fun thongKeTheoNam(userId: Int, nam: Int) {
        viewModelScope.launch {
            _thongKeTheoNamuiState.value = UiState.Loading
            try {
                val result = repository.thongkeTheoNam(userId, nam)
                if (result.success) {
                    _thongKeTheoNamuiState.value = UiState.Success(result.data!!)
                    Log.d("THU_NHAP_VIEWMODEL", "Dữ liệu nhận được: ${result.data}")
                } else {
                    _thongKeTheoNamuiState.value = UiState.Error(result.message ?: "Lỗi không xác định")
                    Log.e("THU_NHAP_VIEWMODEL", "Lỗi từ API: ${result.message}")
                }
            } catch (e: Exception) {
                _thongKeTheoNamuiState.value = UiState.Error(e.message ?: "Lỗi kết nối")
                Log.e("THU_NHAP_VIEWMODEL", "Exception: ${e.message}")
            }
        }
    }
    fun createThuNhap(thunhap: ThuNhapModel) {
        viewModelScope.launch {
            thuNhapCreateState = UiState.Loading
            try {
                val result = repository.createThuNhap(thunhap)
                thuNhapCreateState = UiState.Success(result)
            } catch (e: Exception) {
                thuNhapCreateState = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteThuNhap(id: Int) {
        viewModelScope.launch {
            deleteThuNhapState = UiState.Loading
            try {
                val result = repository.deleteThuNhap(id)
                if (result.success) {
                    deleteThuNhapState = UiState.Success(result)
                } else {
                    deleteThuNhapState = UiState.Error(result.message)
                }
            } catch (e: Exception) {
                deleteThuNhapState = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }
}

