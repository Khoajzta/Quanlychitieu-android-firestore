package com.example.quanlythuchi_android_firestore.Utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getStartAndEndOfMonth(thang: Int, nam: Int): Pair<String, String> {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, nam)
    calendar.set(Calendar.MONTH, thang - 1)

    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val startOfMonthStr = sdf.format(calendar.time)

    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    val endOfMonthStr = sdf.format(calendar.time)

    return startOfMonthStr to endOfMonthStr
}