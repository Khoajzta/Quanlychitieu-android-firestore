package com.example.quanlythuchi_android_firestore.Utils

fun unifiedToEmoji(unified: String): String {
    return unified.split("-")
        .map { it.toInt(16) }
        .map { Character.toChars(it) }
        .joinToString("") { String(it) }
}

