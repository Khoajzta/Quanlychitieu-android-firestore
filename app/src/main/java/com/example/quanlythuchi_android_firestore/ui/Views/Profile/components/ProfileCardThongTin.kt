package com.example.quanlythuchi_android_firestore.ui.Views.Profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlythuchi_android_firestore.ui.Views.Profile.ProfileInfoRow
import com.github.kittinunf.fuel.android.BuildConfig

@Composable
fun AppSettingCard(
    modifier: Modifier = Modifier,
    isDarkMode: MutableState<Boolean>
) {
    val context = LocalContext.current
    val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val versionName = packageInfo.versionName

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode.value)
                Color(0xFF1E1E1E)
            else
                Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Phiên bản ứng dụng
            ProfileInfoRow(
                title = "Phiên bản ứng dụng",
                value = versionName.toString(),
                isDark = isDarkMode.value
            )

            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = if (isDarkMode.value)
                    Color(0xFF2C2C2C)
                else
                    Color(0xFFE0E0E0)
            )

            // Chế độ tối
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Chế độ tối",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDarkMode.value) Color.White else Color.Black
                )

                Switch(
                    checked = isDarkMode.value,
                    onCheckedChange = { isDarkMode.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF2196F3),
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color.Gray
                    )
                )
            }
        }
    }
}
