package com.example.quanlythuchi_android_firestore.feature.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

/**
 * 🎬 Hiệu ứng TRƯỢT VÀO TỪ PHẢI → TRÁI (dùng khi chuyển TIẾN: Login -> Home)
 */

private const val DURATION = 400
fun truotVaoTuPhai(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(DURATION, easing = FastOutSlowInEasing)
    ) + fadeIn(
        initialAlpha = 0.3f,
        animationSpec = tween(DURATION)
    )
}

/**
 * 🎞️ Hiệu ứng TRƯỢT RA SANG TRÁI (dùng khi rời trang hiện tại sang trang kế tiếp)
 */
fun truotRaSangTrai(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(DURATION, easing = FastOutSlowInEasing)
    ) + fadeOut(animationSpec = tween(DURATION))
}

/**
 * ⬅️ Hiệu ứng TRƯỢT VÀO TỪ TRÁI (dùng khi back lại màn hình trước)
 */
fun truotVaoTuTrai(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(DURATION, easing = FastOutSlowInEasing)
    ) + fadeIn(
        initialAlpha = 0.3f,
        animationSpec = tween(DURATION)
    )
}

/**
 * ➡️ Hiệu ứng TRƯỢT RA SANG PHẢI (dùng khi pop ra khỏi màn hình hiện tại)
 */
fun truotRaSangPhai(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(DURATION, easing = FastOutSlowInEasing)
    ) + fadeOut(animationSpec = tween(DURATION))
}

fun truotVaoTuDuoi(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Up,
        animationSpec = tween(DURATION, easing = FastOutSlowInEasing)
    ) + fadeIn(initialAlpha = 0.2f, animationSpec = tween(DURATION))
}

/**
 * ⬇️ Trượt ra xuống dưới (đóng popup hoặc quay lại từ màn chi tiết)
 */
fun truotRaXuongDuoi(): AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(DURATION, easing = FastOutSlowInEasing)
    ) + fadeOut(animationSpec = tween(DURATION))
}
