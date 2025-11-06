package com.example.quanlythuchi_android_firestore.ui.auth

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.ui.ViewModels.NguoiDungViewModel
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


@Composable
fun rememberGoogleSignIn(
    viewModel: NguoiDungViewModel,
    navController: NavController
): () -> Unit {
    val context = LocalContext.current

    // 1. Google Client
    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("421455894843-e6m49anlu68s7mg3glcrr953it6t1cg4.apps.googleusercontent.com")
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    // 2. Launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                viewModel.loginWithGoogle(idToken)
            }
        } catch (e: Exception) {
            Log.e("GoogleLogin", "Error: ${e.message}")
        }
    }

    // 3. Lắng nghe state
    val loginState = viewModel.loginState

    LaunchedEffect(loginState) {
        if (loginState is UiState.Success) {
            val user = loginState.data

            viewModel.handleLoginAndCheckUser(
                nguoiDung = user,
                onSuccess = { userId ->
                    Log.d("userid google", userId.toString())
                    navController.navigate(Screen.Home.createRoute(userId)) {
                        popUpTo(0)
                    }
                },
                onError = { error ->
                    Log.e("CHECK_EMAIL_ERROR", error)
                }
            )
        }
    }


    return {
        googleSignInClient.signOut().addOnCompleteListener {
            launcher.launch(googleSignInClient.signInIntent)
        }
    }
}

