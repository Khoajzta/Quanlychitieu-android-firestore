package com.example.quanlythuchi_android_firestore.ui.Views.Trade.Components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quanlythuchi_android_firestore.Components.CardKhoanChi
import com.example.quanlythuchi_android_firestore.Components.CardThuNhapSwipeToDelete
import com.example.quanlythuchi_android_firestore.Components.DotLoading
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel
import com.example.quanlythuchi_android_firestore.ui.ViewModels.ThuNhapViewModel
import com.example.quanlythuchi_android_firestore.ui.components.CustomSnackbar
import com.example.quanlythuchi_android_firestore.ui.components.SnackbarType
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen
import com.example.quanlythuchi_android_firestore.ui.state.UiState
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceMedium
import kotlinx.coroutines.delay
import java.time.LocalDate
import kotlin.math.abs

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TradeTabPage(
    navController: NavController,
    listKhoanChi: List<KhoanChiModel>,
    userId: Int
) {
    val tabs = listOf("Chi tiêu", "Thu nhập")
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // --- Thanh tab ---
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .fillMaxWidth()
                .background(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(25)
                )
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTabIndex == index
                    val backgroundColor =
                        if (isSelected) Color.White else Color.Transparent
                    val textColor =
                        if (isSelected) Color(0xFF1C94D5) else Color.Black.copy(alpha = 0.8f)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(25))
                            .background(backgroundColor)
                            .clickable { selectedTabIndex = index }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            color = textColor,
                            fontSize = 15.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        // --- Nội dung có thể vuốt ---
        var dragOffset by remember { mutableStateOf(0f) }
        val dragThreshold = 100 // Vuốt ít nhất 100px mới đổi tab

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(selectedTabIndex) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (abs(dragOffset) > dragThreshold) {
                                if (dragOffset > 0 && selectedTabIndex > 0) {
                                    selectedTabIndex--
                                } else if (dragOffset < 0 && selectedTabIndex < tabs.lastIndex) {
                                    selectedTabIndex++
                                }
                            }
                            dragOffset = 0f
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            dragOffset += dragAmount
                        }
                    )
                }
        ) {
            AnimatedContent(
                targetState = selectedTabIndex,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { it } + fadeIn() togetherWith
                                slideOutHorizontally { -it } + fadeOut()
                    } else {
                        slideInHorizontally { -it } + fadeIn() togetherWith
                                slideOutHorizontally { it } + fadeOut()
                    }
                },
                label = "tabSwipe"
            ) { index ->
                when (index) {
                    0 -> ChiTieuPage(navController, listKhoanChi, userId)
                    1 -> ThuNhapPage(userId)
                }
            }
        }
    }
}


@Composable
fun ChiTieuPage(
    navController: NavController,
    listKhoanChi: List<KhoanChiModel>,
    userId:Int
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = PaddingBody),
        verticalArrangement = Arrangement.spacedBy(SpaceMedium)
    ) {
        itemsIndexed(listKhoanChi) { index, item ->
            CardKhoanChi(item, modifier = Modifier, onDetailClick = {navController.navigate(
                Screen.KhoanChiDetail.createRoute(
                    id_khoanChi = item.id,
                    userId = userId
                )
            )})
        }
    }
}



@Composable
fun ThuNhapPage(
    userId: Int,
    thuNhapViewModel: ThuNhapViewModel = hiltViewModel()
) {
    val thuNhapState by thuNhapViewModel.uiState.collectAsState()

    val deleteThuNhapState = thuNhapViewModel.deleteThuNhapState

    val currentDate = LocalDate.now()
    val currentMonth = currentDate.monthValue
    val currentYear = currentDate.year

    var snackbarVisible by remember { mutableStateOf(false) }
    var snackbarType by remember { mutableStateOf(SnackbarType.SUCCESS) }
    var snackbarMessage by remember { mutableStateOf("") }

    // Tải danh sách thu nhập ban đầu
    LaunchedEffect(userId) {
        thuNhapViewModel.getThuNhapTheoThang(
            userId = userId, thang = currentMonth, nam = currentYear
        )
    }

    // Khi xóa thành công -> load lại danh sách
    LaunchedEffect(deleteThuNhapState) {
        if (deleteThuNhapState is UiState.Success) {
            thuNhapViewModel.getThuNhapTheoThang(
                userId = userId, thang = currentMonth, nam = currentYear
            )

            snackbarType = SnackbarType.SUCCESS
            snackbarMessage = "Xóa thu nhập thành công"
            snackbarVisible = true
        }
    }

    val listThuNhap = when (thuNhapState) {
        is UiState.Success -> (thuNhapState as UiState.Success<List<ThuNhapModel>>).data
        else -> emptyList()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        when(thuNhapState){
            is UiState.Success ->{
                if(listThuNhap.isEmpty()){
                    Text(
                        text = "Chưa có thu nhập nào trong tháng",
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }else{
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = PaddingBody),
                        verticalArrangement = Arrangement.spacedBy(SpaceMedium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(listThuNhap, key = { it.id }) { item ->
                            CardThuNhapSwipeToDelete(
                                thuNhap = item,
                                onDelete = { thuNhap ->
                                    thuNhapViewModel.deleteThuNhap(thuNhap.id)
                                }
                            )
                        }
                    }
                }

            }
            is UiState.Error -> Text("Lỗi: ${(thuNhapState as UiState.Error).message}")
            else -> DotLoading()
        }


        AnimatedVisibility(
            visible = snackbarVisible,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(bottom = 16.dp),
            enter = slideInVertically { -it } + fadeIn(),
            exit = slideOutVertically { -it } + fadeOut()
        ) {
            CustomSnackbar(
                message = snackbarMessage,
                type = snackbarType
            )
        }

        LaunchedEffect(snackbarVisible) {
            if (snackbarVisible) {
                delay(1500)
                snackbarVisible = false
            }
        }
    }

}




@Composable
@Preview
fun TradeTabPagePreview(){
//    TradeTabPage(listKhoanChi, thuNhapListSample)
//    ThuNhapPage(listThuNhap)
}