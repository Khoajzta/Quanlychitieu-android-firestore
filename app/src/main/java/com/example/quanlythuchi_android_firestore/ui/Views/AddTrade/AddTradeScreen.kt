package com.example.quanlythuchi_android_firestore.ui.Views.AddTrade

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.DotLoading
import com.example.quanlythuchi_android_firestore.ViewModels.KhoanChiViewModel
import com.example.quanlythuchi_android_firestore.Views.AddTrade.AddTradeTab
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.TaiKhoanViewModel
import com.example.quanlythuchi_android_firestore.ui.components.Header
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.example.quanlythuchi_android_firestore.ui.theme.BackgroundColor
import kotlinx.coroutines.delay

@Composable
fun AddTradeScreen(
    navController: NavController,
    userId : String,
    khoanChiViewModel: KhoanChiViewModel = hiltViewModel(),
    taiKhoanViewModel: TaiKhoanViewModel = hiltViewModel()
) {

    val KhoanChiuiState by khoanChiViewModel.getAllByUserState.collectAsState()
    val taiKhoanUiState by taiKhoanViewModel.getAlltaikhoanState.collectAsState()

    LaunchedEffect(userId) {
        khoanChiViewModel.getAllKhoanChiByUser(userId)
        taiKhoanViewModel.getAllTaiKhoanByUser(userId)
        delay(15 * 60 * 1000L)
    }

    val khoanChiList = when (KhoanChiuiState) {
        is UiState.Success -> (KhoanChiuiState as UiState.Success<List<KhoanChiModel>>).data
        else -> emptyList()
    }

    val taikhoanList = when (taiKhoanUiState) {
        is UiState.Success -> (taiKhoanUiState as UiState.Success<List<TaiKhoanModel>>).data
        else -> emptyList()
    }

    val taiKhoanChinh: TaiKhoanModel? = taikhoanList.firstOrNull { it.loai_taikhoan == 1 }


    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            Header(
                navController,
                Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = "Thêm giao dịch",
                userId
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(KhoanChiuiState is UiState.Success && taiKhoanUiState is UiState.Success){
                AddTradeTab(navController,khoanChiList, taikhoanchinh = taiKhoanChinh!!, userId)
            }else{
                DotLoading()
            }

        }
    }
}

@Composable
@Preview
fun AddTradeScreenPreview (){
//    var navController = rememberNavController()
//
//    AddTradeScreen(navController,listKhoanChi)
}
