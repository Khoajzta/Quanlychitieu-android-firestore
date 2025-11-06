package com.example.quanlythuchi_android_firestore.Utils

import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel
import java.time.LocalDate

fun tinhTongTheoTuanVaNgay(
    chitieulist: List<ChiTieuModel>,
    thunhaplist: List<ThuNhapModel>
): Pair<Map<String, Long>, List<String>> {
    val today = LocalDate.now()
    val startOfWeek = today.with(java.time.DayOfWeek.MONDAY)
    val weekDates = (0..6).map { startOfWeek.plusDays(it.toLong()) }

    val data = weekDates.associate { date ->
        val chiTieuTrongNgay = chitieulist
            .filter {
                runCatching { LocalDate.parse(it.ngay_tao.substring(0, 10)) == date }
                    .getOrDefault(false)
            }
            .sumOf { it.so_tien }

        val thuNhapTrongNgay = thunhaplist
            .filter {
                runCatching { LocalDate.parse(it.ngay_tao.substring(0, 10)) == date }
                    .getOrDefault(false)
            }
            .sumOf { it.so_tien }

        val tong = thuNhapTrongNgay - chiTieuTrongNgay

        val dayLabel = when (date.dayOfWeek.value) {
            1 -> "T2"
            2 -> "T3"
            3 -> "T4"
            4 -> "T5"
            5 -> "T6"
            6 -> "T7"
            else -> "CN"
        }

        dayLabel to tong
    }

    val dates = weekDates.map { "${it.dayOfMonth}/${it.monthValue}" }
    return Pair(data, dates)
}

