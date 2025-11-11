package com.example.quanlythuchi_android_firestore.data.local.Notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.quanlythuchi_android_firestore.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("NotificationReceiver", "🔥 Đã nhận broadcast!")

        if (context == null) {
            Log.e("NotificationReceiver", "Context null ❌")
            return
        }

        val userId = intent?.getStringExtra("userId") ?: return

        val db = FirebaseFirestore.getInstance()
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        db.collection("chitieu")
            .whereEqualTo("id_nguoidung", userId)
            .whereEqualTo("ngay", today)
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    Log.d("NotificationReceiver", "📭 Chưa có chi tiêu hôm nay, hiển thị thông báo.")
                    showNotification(context)
                } else {
                    Log.d("NotificationReceiver", "✅ Đã có chi tiêu hôm nay, không thông báo.")
                }
            }
            .addOnFailureListener { e ->
                Log.e("NotificationReceiver", "❌ Lỗi khi kiểm tra chi tiêu: ${e.message}")
            }
    }

    private fun showNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = "daily_notification_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Thông báo hàng ngày",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("💸 Nhắc nhở chi tiêu")
            .setContentText("Đừng quên thêm chi tiêu hôm nay bạn nhé!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(1001, builder.build())
    }
}


