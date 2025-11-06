package com.example.quanlythuchi_android_firestore.domain.respository

import com.example.quanlythuchi_android_firestore.domain.model.NguoiDungModel
import com.example.quanlythuchi_android_firestore.ui.state.UiState

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): UiState<NguoiDungModel>
}
