package com.example.quanlythuchi_android_firestore.ui.Views.ThongKeTheoNam.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.quanlythuchi_android_firestore.Utils.formatCurrencyShort
import com.example.quanlythuchi_android_firestore.domain.model.ThongKeThangModel
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusXL

@Composable
fun BieuDoThongKe(
    modifier: Modifier = Modifier,
    chartHeight: Dp = 400.dp,
    barWidth: Dp = 28.dp,
    barSpace: Dp = 24.dp,
    thongKeList: List<ThongKeThangModel>,
    maxValue: Float
) {
    // 🎨 Shadow elevation đẹp
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(5.dp, RoundedCornerShape(RadiusXL))
            .clip(RoundedCornerShape(RadiusXL))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFDFDFD), RoundedCornerShape(20.dp))
                .padding(vertical = 16.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Canvas(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .width((thongKeList.size * (barWidth * 2 + barSpace)))
                            .height(chartHeight)
                    ) {
                        val barW = barWidth.toPx()
                        val space = barSpace.toPx()
                        val chartH = size.height - 60f // chừa chỗ nhãn và số tiền

                        thongKeList.forEachIndexed { index, tk ->
                            val chiHeight = (tk.tongChi / maxValue) * chartH
                            val thuHeight = (tk.tongThu / maxValue) * chartH
                            val xChi = index * (barW * 2 + space)
                            val xThu = xChi + barW + 6f

                            val chiBrush = Brush.verticalGradient(
                                listOf(Color(0xFFFF6B6B), Color(0xFFFFA36C))
                            )
                            val thuBrush = Brush.verticalGradient(
                                listOf(Color(0xFF4ECDC4), Color(0xFF1A9FFF))
                            )

                            // Cột Chi
                            drawRoundRect(
                                brush = chiBrush,
                                topLeft = Offset(xChi, chartH - chiHeight),
                                size = Size(barW, chiHeight),
                                cornerRadius = CornerRadius(10f, 10f)
                            )

                            // Cột Thu
                            drawRoundRect(
                                brush = thuBrush,
                                topLeft = Offset(xThu, chartH - thuHeight),
                                size = Size(barW, thuHeight),
                                cornerRadius = CornerRadius(10f, 10f)
                            )

                            // Vẽ tổng chi và thu
                            drawContext.canvas.nativeCanvas.apply {
                                val paint = android.graphics.Paint().apply {
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    textSize = 26f
                                    color = android.graphics.Color.DKGRAY
                                    isFakeBoldText = true
                                }

                                drawText(
                                    formatCurrencyShort(tk.tongChi),
                                    xChi + barW / 2,
                                    chartH - chiHeight - 10f,
                                    paint
                                )

                                drawText(
                                    formatCurrencyShort(tk.tongThu),
                                    xThu + barW / 2,
                                    chartH - thuHeight - 10f,
                                    paint
                                )

                                drawText(
                                    "T${tk.thang}",
                                    (xChi + xThu + barW) / 2,
                                    chartH + 50f,
                                    android.graphics.Paint().apply {
                                        textAlign = android.graphics.Paint.Align.CENTER
                                        textSize = 32f
                                        color = android.graphics.Color.DKGRAY
                                        isFakeBoldText = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
