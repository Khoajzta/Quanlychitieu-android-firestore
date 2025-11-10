package com.example.quanlythuchi_android_firestore.Components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen

@Composable
fun ButtonBackToHome(
    navController: NavController,
    userId: String
) {
    val context = LocalContext.current

    IconButton(onClick = {
        navController.navigate(Screen.Home.createRoute(userId)) {
            popUpTo(0)
            launchSingleTop = true
        }
    }) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data("file:///android_asset/icons/ic_home.svg") // 👉 đường dẫn SVG
                .decoderFactory(SvgDecoder.Factory())
                .build(),
            contentDescription = "Home",
            modifier = Modifier.size(22.dp),
            colorFilter = ColorFilter.tint(Color.Black) // đổi sang Color.White nếu cần
        )
    }
}
