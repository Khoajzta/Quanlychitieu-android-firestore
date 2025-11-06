package com.example.quanlythuchi_android_firestore.Views.login

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.example.quanlythuchi_android_firestore.R
import com.example.quanlythuchi_android_firestore.Views.login.components.ButtonLoginGoogle
import com.example.quanlythuchi_android_firestore.ui.ViewModels.NguoiDungViewModel
import com.example.quanlythuchi_android_firestore.ui.auth.rememberGoogleSignIn
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusXL
import java.time.LocalDate

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: NguoiDungViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit = {}
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val onGoogleLoginClick = rememberGoogleSignIn(viewModel, navController)

    // Hiệu ứng cho logo
    val scale = remember { Animatable(0.8f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
        )
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 700, delayMillis = 300)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
    ) {
        // Hiệu ứng loading overlay
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo + Text
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

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Chào mừng trở lại!",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Đăng nhập để tiếp tục quản lý chi tiêu của bạn",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Card đăng nhập
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(RadiusXL))
                    .background(Color.White.copy(alpha = 0.15f))

                    .padding(vertical = 32.dp, horizontal = 24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ButtonLoginGoogle(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onGoogleLoginClick
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Đăng nhập bằng tài khoản Google",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Footer
            Text(
                text = "© ${LocalDate.now().year} FinTrack | Kiểm soát chi tiêu thông minh",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview
@Composable
fun PreviewLoginScreen() {
}
