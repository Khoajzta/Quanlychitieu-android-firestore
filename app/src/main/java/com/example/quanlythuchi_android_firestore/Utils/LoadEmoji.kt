package com.example.quanlythuchi_android_firestore.Utils

import android.content.Context
import org.json.JSONArray

fun loadEmojiList(context: Context): List<String> {
    val json = context.assets.open("emoji.json").bufferedReader().use { it.readText() }
    val jsonArray = JSONArray(json)
    val emojis = mutableListOf<String>()

    for (i in 0 until jsonArray.length()) {
        val obj = jsonArray.getJSONObject(i)
        val unified = obj.optString("unified")

        if (unified.isNotBlank()) {
            emojis.add(unifiedToEmoji(unified))
        }
    }

    return emojis
}


