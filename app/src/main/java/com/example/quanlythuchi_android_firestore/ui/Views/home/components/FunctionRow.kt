package com.example.quanlythuchi_android_firestore.ui.Views.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingMedium
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusFull

@Composable
fun CardFunction(
    modifier: Modifier = Modifier,
    title: String,
    gradientColors: List<Color>,
    iconPath: String,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val iconColor = Color.White

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(RadiusFull))
            .background(
                Brush.linearGradient(
                    colors = gradientColors,
                    start = Offset(0f, 0f),
                    end = Offset(300f, 300f)
                )
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(iconPath)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = title,
                modifier = Modifier
                    .size(28.dp)
                    .padding(end = 8.dp),
                colorFilter = ColorFilter.tint(iconColor)
            )

            Text(
                text = title,
                color = Color.White,
                softWrap = true,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                lineHeight = 16.sp,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
    }
}

@Composable
fun FunctionRow(
    navController: NavController,
    userId: Int
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = PaddingMedium),
        horizontalArrangement = Arrangement.spacedBy(PaddingMedium),
    ) {
        // 🟦 Màu dịu, thể hiện thống kê / dữ liệu / niềm tin
        item {
            CardFunction(
                title = "Thống kê trong năm",
                gradientColors = listOf(
                    Color(0xFF4F7DF3), // Xanh dương tươi
                    Color(0xFF6BA9FF)  // Xanh nhạt pha tím
                ),
                iconPath = "file:///android_asset/icons/ic_chart.svg",
                onClick = {
                    navController.navigate(Screen.ThongKeNam.createRoute(userId = userId))
                }
            )
        }

        // 🟩 Màu tươi sáng, gợi cảm giác hành động, thêm mới
        item {
            CardFunction(
                title = "Thêm khoản chi",
                gradientColors = listOf(
                    Color(0xFF22C55E), // Xanh lá tươi (Material Success)
                    Color(0xFFFFA726)  // Cam nhẹ (nổi bật)
                ),
                iconPath = "file:///android_asset/icons/ic_add_khoan_chi.svg",
                onClick = {
                    navController.navigate(Screen.AddKhoanChi.createRoute(userId = userId))
                }
            )
        }
    }
}




@Composable
@Preview()
fun PreviewFunctionRow() {

}