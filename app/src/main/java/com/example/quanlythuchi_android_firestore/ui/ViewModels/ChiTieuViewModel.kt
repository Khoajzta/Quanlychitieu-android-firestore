package com.example.quanlythuchi_android_firestore.ui.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.respository.ChiTieuRespository
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChiTieuViewModel @Inject constructor(
    private val repository: ChiTieuRespository
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ChiTieuModel>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ChiTieuModel>>> = _uiState

    private val _uiStateTheoThangTruoc = MutableStateFlow<UiState<List<ChiTieuModel>>>(UiState.Loading)
    val uiStateTheoThangTruoc: StateFlow<UiState<List<ChiTieuModel>>> = _uiStateTheoThangTruoc
    private val _uiStateTheoThang = MutableStateFlow<UiState<List<ChiTieuModel>>>(UiState.Loading)
    val uiStateTheoThang: StateFlow<UiState<List<ChiTieuModel>>> = _uiStateTheoThang
    var createChiTieuState by mutableStateOf<UiState<BaseResponse<ChiTieuModel>>>(UiState.Loading)
        private set

    private val _deleteChiTieuState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val deleteChiTieuState: StateFlow<UiState<StatusResponse>> = _deleteChiTieuState

    private val _thongKeTheoNamuiState = MutableStateFlow<UiState<List<ThongKeChiTieuModel>>>(UiState.Loading)
    val thongKeTheoNam: StateFlow<UiState<List<ThongKeChiTieuModel>>> = _thongKeTheoNamuiState

    fun getChiTieuTheoKhoanChiCuaUser(id_khoanchi: Int, userId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = repository.getChiTieuTheoKhoanChiCuaNguoiDung(id_khoanchi = id_khoanchi, userId = userId)
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }
        }
    }

    fun getChiTieuTheoThangVaNam(userId :Int, thang:Int, nam: Int) {
        viewModelScope.launch {
            _uiStateTheoThang.value = UiState.Loading
            try {
                val result = repository.getChiTieuTheoThangVaNam(userId = userId, thang = thang, nam = nam)
                _uiStateTheoThang.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiStateTheoThang.value = UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }
        }
    }

    fun getChiTieuTheoThangTruoc(userId :Int, thang:Int, nam: Int) {
        viewModelScope.launch {
            _uiStateTheoThangTruoc.value = UiState.Loading
            try {
                val result = repository.getChiTieuTheoThangVaNam(userId = userId, thang = thang, nam = nam)
                _uiStateTheoThangTruoc.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiStateTheoThangTruoc.value = UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }
        }
    }

    fun thongKeChiTieuTheoNam(userId :Int, nam: Int) {
        viewModelScope.launch {
            _thongKeTheoNamuiState.value = UiState.Loading
            try {
                val result = repository.thongKeTheoNam(userId, nam)
                if (result.success) {
                    _thongKeTheoNamuiState.value = UiState.Success(result.data!!)
                    Log.d("ChiTieu_ViewModel", "Dữ liệu nhận được: ${result.data}")
                } else {
                    _thongKeTheoNamuiState.value = UiState.Error(result.message ?: "Lỗi không xác định")
                    Log.e("ChiTieu_ViewModel", "Lỗi từ API: ${result.message}")
                }
            } catch (e: Exception) {
                _thongKeTheoNamuiState.value = UiState.Error(e.message ?: "Lỗi kết nối")
                Log.e("ChiTieu_ViewModel", "Exception: ${e.message}")
            }
        }
    }

    fun createChiTieu(chitieu: ChiTieuModel) {
        viewModelScope.launch {
            createChiTieuState = UiState.Loading
            try {
                val result = repository.createChiTieu(chitieu)
                createChiTieuState = UiState.Success(result)
            } catch (e: Exception) {
                createChiTieuState = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteChiTieu(id:Int){
        viewModelScope.launch {
            _deleteChiTieuState.value = UiState.Loading
            try {
                val result = repository.deleteChiTieu(id)
                if (result.success) {
                    _deleteChiTieuState.value = UiState.Success(result)
                } else {
                    _deleteChiTieuState.value = UiState.Error(result.message)
                }
            } catch (e: Exception) {
                _deleteChiTieuState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }
}