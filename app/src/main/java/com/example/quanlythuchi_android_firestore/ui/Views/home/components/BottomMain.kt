package com.example.quanlythuchi_android_firestore.Views.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen

// Dữ liệu mỗi mục trong bottom bar
data class BottomNavItem(
    val title: String,
    val iconPath: String, // đổi từ iconRes sang đường dẫn SVG
    val route: String
)

@Composable
fun BottomNavigationBar(
    navController: NavController? = null,
    modifier: Modifier = Modifier,
    userId: Int
) {
    val items = listOf(
        BottomNavItem("Home", "file:///android_asset/icons/ic_home.svg", Screen.Home.route),
        BottomNavItem("Giao dịch", "file:///android_asset/icons/ic_trade.svg", Screen.Trade.route),
        BottomNavItem("Ngân sách", "file:///android_asset/icons/ic_wallet.svg", Screen.NganSach.route),
        BottomNavItem("Cá nhân", "file:///android_asset/icons/ic_profile.svg", Screen.Profile.route)
    )

    val currentRoute = navController?.currentBackStackEntryAsState()?.value?.destination?.route

    Row(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .shadow(
                    5.dp,
                    RoundedCornerShape(50.dp),
                    ambientColor = Color(0xFF6FBAD6),
                )
                .clip(RoundedCornerShape(50.dp)),
            contentAlignment = Alignment.Center
        ) {
            // Nền gradient mờ
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .blur(18.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF9FD7EE), Color(0xFF6FBAD6))
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    BottomBarItem(
                        iconPath = item.iconPath,
                        title = item.title,
                        isSelected = currentRoute == item.route,
                        onClick = {
                            if (navController != null && currentRoute != item.route) {
                                val routeToNavigate = when (item.route) {
                                    Screen.Trade.route -> Screen.Trade.createRoute(userId)
                                    Screen.NganSach.route -> Screen.NganSach.createRoute(userId)
                                    Screen.Profile.route -> Screen.Profile.createRoute(userId)
                                    else -> item.route
                                }

                                navController.navigate(routeToNavigate) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBarItem(
    iconPath: String,
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val iconColor = if (isSelected) Color(0xFF1C94D5) else Color.White
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(iconPath)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = title,
                modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(iconColor)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BottomNavigationBarPreview() {

}
