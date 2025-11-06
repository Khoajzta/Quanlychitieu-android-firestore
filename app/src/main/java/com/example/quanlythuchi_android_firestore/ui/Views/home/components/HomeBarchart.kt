package com.example.quanlythuchi_android_firestore.Views.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quanlythuchi_android_firestore.Utils.formatCurrencyShort
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusXL
import kotlin.math.abs

@Composable
fun WeeklyFinanceBarChart(
    modifier: Modifier = Modifier,
    data: Map<String, Long>,
    dates: List<String>
) {
    val maxAmount = (data.values.maxOfOrNull { abs(it) } ?: 0).coerceAtLeast(1)
    val dayKeys = listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")

    val dynamicHeight = remember(maxAmount) {
        val baseHeight = 120.dp
        val extraHeight = (maxAmount / 1_000_000).coerceAtMost(5) * 20
        (baseHeight.value + extraHeight).dp.coerceAtMost(280.dp)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(5.dp, RoundedCornerShape(RadiusXL))
            .clip(RoundedCornerShape(RadiusXL))
            .background(Color.White) // ✅ Nền trắng
    ) {
        Column(
            modifier = Modifier
                .background(Color.White, RoundedCornerShape(15.dp)) // ✅ Nền trắng trong khung
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dynamicHeight)
                    .padding(horizontal = 16.dp, vertical = 20.dp)
                    .background(Color.White) // ✅ Nền trắng trong vùng vẽ
            ) {
                val barWidth = size.width / (dayKeys.size * 2)
                val centerY = size.height / 2

                drawLine(
                    color = Color.Gray.copy(alpha = 0.3f),
                    start = Offset(0f, centerY),
                    end = Offset(size.width, centerY),
                    strokeWidth = 2f
                )

                dayKeys.forEachIndexed { index, key ->
                    val amount = data[key]
                    if (amount != null && amount != 0L) {
                        val barHeight = (size.height / 2) * (abs(amount).toFloat() / maxAmount)
                        val barX = (index * 2 + 1) * barWidth

                        val brush = if (amount > 0) {
                            Brush.verticalGradient(
                                listOf(Color(0xFF4CAF50), Color(0xFF2E7D32))
                            )
                        } else {
                            Brush.verticalGradient(
                                listOf(Color(0xFFE53935), Color(0xFFB71C1C))
                            )
                        }

                        drawRoundRect(
                            brush = brush,
                            topLeft = Offset(barX - barWidth / 2, centerY - if (amount > 0) barHeight else 0f),
                            size = Size(barWidth, barHeight),
                            cornerRadius = CornerRadius(20f, 20f)
                        )

                        val prefix = if (amount > 0) "+" else "-"
                        val displayAmount = "$prefix${formatCurrencyShort(abs(amount))}"

                        val textPaint = Paint().asFrameworkPaint().apply {
                            isAntiAlias = true
                            textSize = 30f
                            color = if (amount > 0)
                                android.graphics.Color.parseColor("#2E7D32")
                            else
                                android.graphics.Color.parseColor("#C62828")
                            textAlign = android.graphics.Paint.Align.CENTER
                            isFakeBoldText = true
                        }

                        val textY = if (amount > 0) {
                            centerY - barHeight - 10
                        } else {
                            centerY + barHeight + 35
                        }

                        drawContext.canvas.nativeCanvas.drawText(
                            displayAmount,
                            barX,
                            textY,
                            textPaint
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                dayKeys.forEachIndexed { index, key ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = key,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = dates.getOrNull(index) ?: "",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun PreviewWeeklyFinanceChart() {
    val weeklyData = mapOf(
        "T2" to 200000L,
        "T3" to -80000L,
        "T4" to 200000L,
        "T5" to 50000L,
        "T6" to -50000L,
        "T7" to -50000L,
        "CN" to 50000L,
    )
    val weekDates = listOf("07", "08", "09", "10", "11", "12", "13")

    WeeklyFinanceBarChart(data = weeklyData, dates = weekDates)
}





