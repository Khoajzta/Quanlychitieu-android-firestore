package com.example.quanlythuchi_android_firestore.Utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun formatDateToDB(input: String): String {
    return try {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = inputFormat.parse(input)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        input
    }
}

fun formatMillisToDB(millis: Long?): String {
    if (millis == null) return ""
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(millis))
}

fun formatDayDisplay(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())  // định dạng chuỗi đầu vào
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // định dạng mong muốn
        val date = inputFormat.parse(dateString)
        if (date != null) outputFormat.format(date) else ""
    } catch (e: Exception) {
        ""
    }
}

fun parseDateToMillis(dateString: String): Long {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(dateString, formatter)
    return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

