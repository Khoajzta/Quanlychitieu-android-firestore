package com.example.quanlythuchi_android_firestore.ui.Views.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quanlythuchi_android_firestore.Components.CustomButton
import com.example.quanlythuchi_android_firestore.Components.DotLoading
import com.example.quanlythuchi_android_firestore.R
import com.example.quanlythuchi_android_firestore.ui.ViewModels.NguoiDungViewModel
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    onNavigateToLogin: () -> Unit,
    viewModel: NguoiDungViewModel = hiltViewModel()
) {
    val userId by viewModel.getUserId().collectAsState(initial = null)
    val isFirstLaunch by viewModel.isFirstLaunch().collectAsState(initial = null)

    val scale = remember { Animatable(0.8f) }
    val alpha = remember { Animatable(0f) }

    // Hiệu ứng xuất hiện logo + text
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, delayMillis = 300)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1A237E), Color(0xFF0D47A1))
                )
            )
    ) {
        Image(
            painter = painterResource(R.drawable.bg_splash),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().alpha(0.25f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.4f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.6f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Logo + Tên ứng dụng
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scale.value,
                        scaleY = scale.value,
                        alpha = alpha.value
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_3),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Kiểm Soát Chi Tiêu",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Tương lai tài chính vững chắc",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }

            // Nút hoặc loading
            when (isFirstLaunch) {
                null -> {
                    DotLoading(modifier = Modifier.scale(1.1f))
                }
                true -> {
                    CustomButton(
                        title = "Bắt đầu ngay",
                        onClick = {
                            viewModel.setFirstLaunch(false)
                            onNavigateToLogin()
                        },
                        modifier = Modifier
                            .graphicsLayer(alpha = alpha.value)
                            .fillMaxWidth(),
                        tralingIcon = Icons.Default.ArrowForward
                    )
                }
                false -> {
                    LaunchedEffect(userId) {
                        delay(1500)
                        if (userId != null) {
                            navController.navigate(Screen.Home.createRoute(userId!!)) {
                                popUpTo(0)
                            }
                        } else {
                            onNavigateToLogin()
                        }
                    }
                    DotLoading(modifier = Modifier.scale(1.1f))
                }
            }
        }
    }
}




@Preview
@Composable
fun PreviewSplashScreen() {
    val navController = rememberNavController()

    SplashScreen(
        navController,
        onNavigateToLogin = {},
    )
}
