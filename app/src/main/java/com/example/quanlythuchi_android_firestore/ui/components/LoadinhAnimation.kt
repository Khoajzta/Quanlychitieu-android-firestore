package com.example.quanlythuchi_android_firestore.Components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DotLoading(
    modifier: Modifier = Modifier,
    dotCount: Int = 3,
    dotSize: Dp = 17.dp,
    dotColor: Color = Color(0xFF1F6BD5), // Màu tím-indigo
    animationDelay: Int = 200
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scales = List(dotCount) { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = dotCount * animationDelay
                    0.5f at animationDelay * index with LinearEasing
                    1.2f at animationDelay * (index + 1) with LinearEasing
                },
                repeatMode = RepeatMode.Reverse
            ),
            label = "dotScale$index"
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        scales.forEach { scale ->
            Box(
                modifier = Modifier
                    .size(dotSize * scale.value)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(dotColor, dotColor.copy(alpha = 0.6f))
                        ),
                        shape = CircleShape
                    )
            )
        }
    }
}

