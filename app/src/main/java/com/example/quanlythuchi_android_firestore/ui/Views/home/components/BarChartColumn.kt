package com.example.quanlythuchi_android_firestore.ui.Views.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlythuchi_android_firestore.Views.home.components.WeeklyFinanceBarChart
import com.example.quanlythuchi_android_firestore.Views.home.components.WeeklyFinanceBarChart

@Composable
fun BarChartColumn(
    data: Map<String, Long>,
    dates: List<String>
){
    Column(

    ) {
        Text(
            "Thống kê trong tuần",
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Spacer(Modifier.height(10.dp))
        WeeklyFinanceBarChart(data = data, dates = dates)
    }
}