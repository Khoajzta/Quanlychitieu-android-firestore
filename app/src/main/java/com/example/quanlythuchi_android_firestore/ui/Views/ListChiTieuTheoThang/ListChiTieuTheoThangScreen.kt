package com.example.quanlythuchi_android_firestore.ui.Views.ListChiTieuTheoThang

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.DotLoading
import com.example.quanlythuchi_android_firestore.ui.ViewModels.ChiTieuViewModel
import com.example.quanlythuchi_android_firestore.ui.components.CardChiTieuSwipeToDelete
import com.example.quanlythuchi_android_firestore.ui.components.Header
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.example.quanlythuchi_android_firestore.ui.theme.BackgroundColor
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceMedium
import java.time.LocalDate

@Composable
fun ListChiTieuTheoThangScreen(
    navController: NavController,
    userId: Int,
    chiTieuViewModel: ChiTieuViewModel = hiltViewModel()
){
    val currentDate = LocalDate.now()
    val currentMonth = currentDate.monthValue
    val currentYear = currentDate.year

    val thunhapListState by chiTieuViewModel.uiStateTheoThang.collectAsState()

    LaunchedEffect(Unit) {
        chiTieuViewModel.getChiTieuTheoThangVaNam(userId, currentMonth, currentYear)
    }

    val chitieuList = when(thunhapListState){
        is UiState.Success -> (thunhapListState as UiState.Success).data
        else -> emptyList()
    }


    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            Header(
                navController = navController,
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                title = "Danh sách chi tiêu tháng ${currentMonth}",
                userId = userId
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            when(thunhapListState){
                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().padding(horizontal = PaddingBody),
                        verticalArrangement = Arrangement.spacedBy(SpaceMedium)
                    ) {
                        if(chitieuList.isNullOrEmpty()){
                            item {
                                Text(
                                    text = "Chưa có chi tiêu nào",
                                    color = Color.Gray,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }else{
                            items(chitieuList){chitieu->
                                CardChiTieuSwipeToDelete(
                                    chitieu = chitieu,
                                    onDelete = { chitieu ->
                                        chiTieuViewModel.deleteChiTieu(chitieu.id)
                                    }
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(30.dp))
                        }
                    }
                }
                is UiState.Error -> {
                    Text("lỗi: ${(thunhapListState as UiState.Error).message}")
                }
                else -> {
                    DotLoading()
                }
            }
        }
    }
}