package com.example.quanlythuchi_android_firestore.ViewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponse
import com.example.quanlythuchi_android_firestore.data.remote.dto.BaseResponseMes
import com.example.quanlythuchi_android_firestore.data.remote.dto.StatusResponse
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.domain.respository.KhoanChiRepository
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KhoanChiViewModel @Inject constructor(
    private val repo: KhoanChiRepository
) : ViewModel() {
    private val _getAllByUserState = MutableStateFlow<UiState<List<KhoanChiModel>>>(UiState.Loading)
    val getAllByUserState: StateFlow<UiState<List<KhoanChiModel>>> = _getAllByUserState

    private val _loadtheothang = MutableStateFlow<UiState<List<KhoanChiModel>>>(UiState.Loading)
    val loadtheothang: StateFlow<UiState<List<KhoanChiModel>>> = _loadtheothang

    private val _createState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val createState: StateFlow<UiState<StatusResponse>> = _createState


    private val _getByIdState = MutableStateFlow<UiState<KhoanChiModel>>(UiState.Loading)
    val getById: StateFlow<UiState<KhoanChiModel>> = _getByIdState
    private val _updateState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val updateState: StateFlow<UiState<StatusResponse>> = _updateState

    private val _deleteState = MutableStateFlow<UiState<StatusResponse>>(UiState.Loading)
    val deleteState: StateFlow<UiState<StatusResponse>> = _deleteState


    fun getAllKhoanChiByUser(userId: String) {
        viewModelScope.launch {
            _getAllByUserState.value = UiState.Loading
            try {
                val result = repo.getKhoanChi(userId)
                _getAllByUserState.value = UiState.Success(result)
            } catch (e: Exception) {
                _getAllByUserState.value = UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }
        }
    }

    fun getKhoanChiTheThangVaNam(userId: String, thang:Int, nam:Int) {
        viewModelScope.launch {
            _loadtheothang.value = UiState.Loading
            try {
                val result = repo.getKhoanChiTheoThangVaNam(userId,thang,nam)
                _loadtheothang.value = UiState.Success(result)
            } catch (e: Exception) {
                _loadtheothang.value = UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }
        }
    }

    fun getKhoanChiById(id_khoanchi: String) {
        viewModelScope.launch {
            _getByIdState.value = UiState.Loading
            try {
                val result = repo.getKhoanChiById(id_khoanchi)
                _getByIdState.value = UiState.Success(result)
            } catch (e: Exception) {
                e.printStackTrace()
                _getByIdState.value = UiState.Error(e.localizedMessage ?: "Lỗi không xác định")
            }
        }
    }


    fun createKhoanChi(khoanchi: KhoanChiModel) {
        viewModelScope.launch {
            _createState.value = UiState.Loading
            try {
                val response = repo.createKhoanChi(khoanchi)
                if (response.success) {
                    _createState.value = UiState.Success(response)
                } else {
                    _createState.value = UiState.Error(response.message ?: "Không thể tạo khoản chi")
                }
            } catch (e: Exception) {
                _createState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun updateKhoanChi(khoanchi: KhoanChiModel) {
        viewModelScope.launch {
            _updateState.value = UiState.Loading
            try {
                val result = repo.updateKhoanChi(khoanchi)
                if (result.success) {
                    _updateState.value = UiState.Success(result)
                } else {
                    _updateState.value = UiState.Error(result.message)
                }
            } catch (e: Exception) {
                _updateState.value = UiState.Error(e.message ?: "Lỗi không xác định")
            }
        }
    }

    fun deleteKhoanChi(id_khoanchi: String) {
        viewModelScope.launch {
            _deleteState.value = UiState.Loading
            try {
                val result = repo.deleteKhoanChi(id_khoanchi)
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
