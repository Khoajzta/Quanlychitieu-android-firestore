package com.example.quanlythuchi_android_firestore.data.respository

import android.util.Log
import com.example.quanlythuchi_android_firestore.domain.model.NguoiDungModel
import com.example.quanlythuchi_android_firestore.domain.respository.AuthRepository
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

fun getIdToken(user: FirebaseUser, onResult: (String?) -> Unit) {
    user.getIdToken(true)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val idToken = task.result?.token
                onResult(idToken)
            } else {
                onResult(null)
            }
        }
}

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signInWithGoogle(idToken: String): UiState<NguoiDungModel> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()

            val user = result.user ?: return UiState.Error("User null")

            Log.d("FIREBASE_AUTH", "User name: ${user.displayName}")

            // Lấy token trực tiếp bằng await()
            val tokenResult = user.getIdToken(true).await()
            val token = tokenResult.token ?: ""

            UiState.Success(
                NguoiDungModel(
                    id = "",
                    ten = user.displayName ?: "",
                    email = user.email ?: "",
                    url_avt = user.photoUrl.toString(),
                    google_id = user.uid,
                    token = token
                )
            )
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Lỗi đăng nhập")
        }
    }

}
