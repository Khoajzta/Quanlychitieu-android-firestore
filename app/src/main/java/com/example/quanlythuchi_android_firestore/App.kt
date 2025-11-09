package com.example.quanlythuchi_android_firestore

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.example.quanlythuchi_android_firestore.data.local.Notification.NotificationReceiver
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import java.util.Calendar
import kotlin.apply
import kotlin.jvm.java

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        // Cho phép quyền báo thức chính xác (Android 12+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

//         testThongBaoNgay() // ← bật dòng này để test ngay
        datThongBao18h()
    }

//    private fun testThongBaoNgay() {
//        val intent = Intent(this, NotificationReceiver::class.java).apply {
//            Intent.setAction = "com.example.quanlythuchi_android_firestore.ALARM_ACTION"
//        }
//
//        sendBroadcast(intent)
//    }

    private fun datThongBao18h() {
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 19)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        Log.d("App", "⏰ Báo thức đặt lúc: ${calendar.time}")

        if (coTheDatBaoThuc(this)) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }

        val triggerTime = calendar.timeInMillis - System.currentTimeMillis()
        Log.d("Appiiiii", "📅 Thời gian chờ: ${triggerTime / 1000} giây")

        if (triggerTime < 60_000) {
            Log.d("Apphhhhh", "⏱ Sắp đến giờ báo, chờ test...")
        }

    }

    private fun coTheDatBaoThuc(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return true
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        return alarmManager.canScheduleExactAlarms()
    }
}



