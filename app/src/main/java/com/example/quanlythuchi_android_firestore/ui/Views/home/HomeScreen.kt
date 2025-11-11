package com.example.quanlythuchi_android_firestore.ui.Views.home

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quanlythuchi_android_firestore.Components.CusTomTextField
import com.example.quanlythuchi_android_firestore.Components.CustomButton
import com.example.quanlythuchi_android_firestore.Components.CustomDatePicker
import com.example.quanlythuchi_android_firestore.Components.CustomDropdown
import com.example.quanlythuchi_android_firestore.Components.DotLoading
import com.example.quanlythuchi_android_firestore.MainActivity
import com.example.quanlythuchi_android_firestore.Utils.formatCurrency
import com.example.quanlythuchi_android_firestore.Utils.formatDateToDB
import com.example.quanlythuchi_android_firestore.Utils.formatMillisToDB
import com.example.quanlythuchi_android_firestore.Utils.formatMillisToDBFirebase
import com.example.quanlythuchi_android_firestore.Utils.tinhTongTheoTuanVaNgay
//import com.example.quanlythuchi_android_firestore.Utils.tinhTongTheoTuanVaNgay
import com.example.quanlythuchi_android_firestore.ViewModels.KhoanChiViewModel
import com.example.quanlythuchi_android_firestore.Views.home.components.BottomNavigationBar
import com.example.quanlythuchi_android_firestore.Views.home.components.HeaderMain
import com.example.quanlythuchi_android_firestore.data.local.Notification.NotificationReceiver
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.domain.model.NguoiDungModel
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.ChiTieuViewModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.NguoiDungViewModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.TaiKhoanViewModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.ThuNhapViewModel
import com.example.quanlythuchi_android_firestore.ui.Views.home.components.BarChartColumn
import com.example.quanlythuchi_android_firestore.ui.Views.home.components.CardTaiKhoanRow
import com.example.quanlythuchi_android_firestore.ui.Views.home.components.FunctionRow
import com.example.quanlythuchi_android_firestore.ui.Views.home.components.HomeChiTieuColumn
import com.example.quanlythuchi_android_firestore.ui.Views.home.components.HomeThuNhapColumn
import com.example.quanlythuchi_android_firestore.ui.Views.home.components.KhoanChiColumn
import com.example.quanlythuchi_android_firestore.ui.Views.home.components.KhoanChiMoreRow
import com.example.quanlythuchi_android_firestore.ui.components.SnackbarType
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.example.quanlythuchi_android_firestore.ui.theme.BackgroundColor
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceMedium
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    userId: String,
    navController: NavController,
    khoanChiViewModel: KhoanChiViewModel = hiltViewModel(),
    taiKhoanViewModel: TaiKhoanViewModel = hiltViewModel(),
    nguoidungViewModel: NguoiDungViewModel = hiltViewModel(),
    thuNhapViewModel: ThuNhapViewModel = hiltViewModel(),
    chiTieuViewModel: ChiTieuViewModel = hiltViewModel()
) {
    //========================= STATES ==========================================
    val khoanChiState by khoanChiViewModel.loadtheothang.collectAsState()
    val taiKhoanState by taiKhoanViewModel.getAlltaikhoanState.collectAsState()
    val thuNhapState by thuNhapViewModel.getByThangVaNamState.collectAsState()
    val thuNhapTruocState by thuNhapViewModel.getByThangTruocState.collectAsState()
    val chiTieuState by chiTieuViewModel.getByThangVaNamState.collectAsState()
    val chiTieuTruocState by chiTieuViewModel.getByThangVaNamTruocState.collectAsState()
    val nguoiDungState by nguoidungViewModel.getByIdState.collectAsState()

//========================= NGÀY THÁNG HIỆN TẠI ==============================
    val currentDate = LocalDate.now()
    val currentMonth = currentDate.monthValue
    val currentYear = currentDate.year

//========================= TẢI DỮ LIỆU =====================================
    LaunchedEffect(userId) {
        taiKhoanViewModel.getAllTaiKhoanByUser(userId)
        nguoidungViewModel.getNguoiDungByID(userId)
        khoanChiViewModel.getKhoanChiTheThangVaNam(userId, currentMonth, currentYear)

        // Kiểm tra nếu tuần hiện tại bắt đầu ở tháng trước → load thêm dữ liệu tháng trước
        val startOfWeek = currentDate.with(java.time.DayOfWeek.MONDAY)
        val isCrossMonth = startOfWeek.monthValue != currentMonth

        // Load chi tiêu & thu nhập
        thuNhapViewModel.getThuNhapTheoThangVaNam(userId, currentMonth, currentYear)
        chiTieuViewModel.getChiTieuTheoThangVaNam(userId, currentMonth, currentYear)

        if (isCrossMonth) {
            thuNhapViewModel.getThuNhapTheoThangTruoc(userId)
            chiTieuViewModel.getChiTieuTheoThangTruoc(userId)
        }
    }


//========================= CHUYỂN UI STATE SANG LIST =======================
    val khoanChiList = (khoanChiState as? UiState.Success)?.data ?: emptyList()
    val taiKhoanList = (taiKhoanState as? UiState.Success)?.data ?: emptyList()

    val thuNhapListTotal = ((thuNhapState as? UiState.Success)?.data ?: emptyList())
    val thuNhapTruocList = ((thuNhapTruocState as? UiState.Success)?.data ?: emptyList())
    val chiTieuListTotal = ((chiTieuState as? UiState.Success)?.data ?: emptyList())
    val chiTieuTruocList = ((chiTieuTruocState as? UiState.Success)?.data ?: emptyList())


    val top5KhoanChi = khoanChiList
        .sortedByDescending { it.so_luong_chi_tieu }
        .take(5)


    val thuNhapList = thuNhapListTotal
        .sortedByDescending { it.ngay_tao }
        .take(5)

    val chiTieuList = chiTieuListTotal
        .sortedByDescending { it.ngay_tao }
        .take(5)

    val tongThuNhap = thuNhapList.sumOf { it.so_tien!! }
    val tongChiTieu = chiTieuListTotal.sumOf { it.so_tien!! }
    val tongTienDuKien = khoanChiList.sumOf { it.so_tien_du_kien!! }

    val (data, dates) = tinhTongTheoTuanVaNgay(
        chiTieuListTotal + chiTieuTruocList,
        thuNhapListTotal + thuNhapTruocList
    )

    //========================= REFRESH =========================================
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            coroutineScope.launch {
                isRefreshing = true
                khoanChiViewModel.getKhoanChiTheThangVaNam(userId, currentMonth, currentYear)
                taiKhoanViewModel.getAllTaiKhoanByUser(userId)
                delay(500)
                isRefreshing = false
            }
        }
    )

    //========================= HEADER ẨN HIỆN ==================================
    val listState = rememberLazyListState()
    var previousOffset by remember { mutableStateOf(0) }
    var targetHeight by remember { mutableStateOf(100.dp) }

    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        val currentOffset = listState.firstVisibleItemScrollOffset
        if (currentOffset > previousOffset + 5) targetHeight = 0.dp
        else if (currentOffset < previousOffset - 5) targetHeight = 100.dp
        previousOffset = currentOffset
    }

    val animatedHeight by animateDpAsState(targetHeight, label = "")

    //========================= GIAO DIỆN =======================================
    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            AnimatedVisibility(visible = animatedHeight > 0.dp) {
                Box(
                    Modifier
                        .height(animatedHeight)
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.statusBars)
                ) {
                    when (nguoiDungState) {
                        is UiState.Success ->

                            HeaderMain(Modifier.fillMaxSize(), user = (nguoiDungState as UiState.Success<NguoiDungModel>).data!!)
                        is UiState.Error ->
                            Text("Lỗi: ${(nguoiDungState as UiState.Error).message}")
                        else -> {}
                    }
                }
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->

        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .pullRefresh(refreshState)
                .padding(PaddingBody),
            contentAlignment = Alignment.Center
        ) {
            val allLoaded =
                khoanChiState is UiState.Success &&
                        taiKhoanState is UiState.Success &&
                        thuNhapState is UiState.Success &&
                        chiTieuState is UiState.Success

            if (allLoaded) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFC7E6F6)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tháng ${currentMonth} năm ${currentYear}",
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black,
                                fontSize = 16.sp,
                            )
                        }

                    }
                    // Tổng quan tài khoản
                    item {
                        CardTaiKhoanRow(
                            listTaiKhoan = taiKhoanList,
                            tongTienDuKien = tongTienDuKien,
                            tongThuNhap = tongThuNhap,
                            tongChiTieu = tongChiTieu
                        )
                    }


                    item { FunctionRow(navController, userId) }

                    // Biểu đồ thống kê
                    if (thuNhapListTotal.isNotEmpty() || chiTieuListTotal.isNotEmpty()) {
                        item {
                            BarChartColumn(data = data, dates = dates)
                        }
                    }

                    item {
                        if (khoanChiList.isEmpty()) {
                            CustomButton(
                                title = "Thêm khoản chi",
                                icon = Icons.Default.AddCircle,
                                onClick = { navController.navigate(Screen.AddKhoanChi.createRoute(userId)) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            KhoanChiMoreRow(modifier = Modifier.padding(horizontal = 10.dp), navController = navController, userId = userId)
                            KhoanChiColumn(top5KhoanChi)
                        }
                    }

                    if (thuNhapList.isNotEmpty()) {
                        item { HomeThuNhapColumn(navController,userId,thuNhapList) }
                    }

                    if (chiTieuList.isNotEmpty()) {
                        item { HomeChiTieuColumn(navController,userId,chiTieuList) }
                    }

                    item { Spacer(Modifier.height(200.dp)) }

                }

                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = refreshState,
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .align(Alignment.TopCenter)
                )
            } else {
                DotLoading(Modifier.align(Alignment.Center))
            }

            BottomNavigationBar(
                navController = navController,
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                userId = userId
            )
        }
    }
}


@Composable
@Preview
fun PreviewMainScreen() {
    var navController = rememberNavController()
    HomeScreen(
        navController = navController,
        userId = "21",
    )
}