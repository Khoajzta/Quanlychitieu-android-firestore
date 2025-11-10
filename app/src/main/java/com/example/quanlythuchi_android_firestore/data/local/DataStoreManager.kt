package com.example.quanlythuchi_android_firestore.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Tạo DataStore
private val Context.dataStore by preferencesDataStore("user_prefs")

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id") // ✅ đổi sang stringPreferencesKey
        private val FIRST_LAUNCH_KEY = booleanPreferencesKey("first_launch")
    }

    // Lưu userId
    suspend fun saveUserId(userId: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
        }
    }

    // Lấy userId
    fun getUserId(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[USER_ID_KEY]
        }
    }

    // Xóa userId (khi đăng xuất)
    suspend fun clearUserId() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
        }
    }

    // Lưu trạng thái mở lần đầu
    suspend fun setFirstLaunch(isFirst: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[FIRST_LAUNCH_KEY] = isFirst
        }
    }

    // Lấy trạng thái mở lần đầu
    fun isFirstLaunch(): Flow<Boolean> {
        return context.dataStore.data.map { prefs ->
            prefs[FIRST_LAUNCH_KEY] ?: true // Mặc định true nếu chưa set
        }
    }
}

