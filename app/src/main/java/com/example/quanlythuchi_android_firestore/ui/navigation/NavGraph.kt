package com.example.quanlythuchi_android_firestore.feature.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quanlythuchi_android_firestore.Views.AddKhoanChi.AddKhoanChiScreen
import com.example.quanlythuchi_android_firestore.Views.ListKhoanChi.ListKhoanChiScreen
import com.example.quanlythuchi_android_firestore.Views.login.LoginScreen
import com.example.quanlythuchi_android_firestore.ui.Views.AddTaiKhoan.AddTaiKhoanScreen
import com.example.quanlythuchi_android_firestore.ui.Views.ChuyenTien.ChuyenTienScreen
import com.example.quanlythuchi_android_firestore.ui.Views.KhoanChiDetail.KhoanChiDetailScreen
import com.example.quanlythuchi_android_firestore.ui.Views.LichSuChuyenTien.LichSuChuyenTienScreen
import com.example.quanlythuchi_android_firestore.ui.Views.ListChiTieuTheoThang.ListChiTieuTheoThangScreen
import com.example.quanlythuchi_android_firestore.ui.Views.ListThuNhapTheoThang.ListThuNhapTheoThangScreen
import com.example.quanlythuchi_android_firestore.ui.Views.ThongKeTheoNam.ThongKeTheoNamScreen
import com.example.quanlythuchi_android_firestore.ui.Views.UpdateKhoanChi.UpdateKhoanChiScreen
import com.example.quanlythuchi_android_firestore.ui.Views.AddTrade.AddTradeScreen
import com.example.quanlythuchi_android_firestore.ui.Views.NganSach.NganSachScreen
import com.example.quanlythuchi_android_firestore.ui.Views.Profile.ProfileScreen
import com.example.quanlythuchi_android_firestore.ui.Views.Trade.TradeScreen
import com.example.quanlythuchi_android_firestore.ui.Views.home.HomeScreen
import com.example.quanlythuchi_android_firestore.ui.Views.splash.SplashScreen
import com.example.quanlythuchi_android_firestore.ui.navigation.Screen


@Composable
fun AppNavGraph(navController: NavHostController) {


    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(
            route = Screen.Splash.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai()
        ) {
            SplashScreen(
                navController,

                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
            )
        }

        composable(
            route = Screen.Login.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai()
        ) {
            LoginScreen(
                navController,
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.Home.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0

            HomeScreen(
                userId = userId,
                navController = navController,
            )
        }


        composable(
            route = Screen.Profile.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0

            ProfileScreen(
                navController = navController,
                userId = userId,
            )
        }


        composable(
            route = Screen.Trade.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0

            TradeScreen(
                navController = navController,
                userId = userId,
            )
        }


        composable(
            route = Screen.NganSach.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0

            NganSachScreen(
                navController = navController,
                userId = userId,
            )
        }


        composable(
            route = Screen.AddTrade.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0

            AddTradeScreen(
                navController = navController,
                userId = userId,
            )
        }

        composable(
            route = Screen.ListKhoanChi.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            ListKhoanChiScreen(
                navController = navController,
                userId = userId,
            )
        }

        composable(
            route = Screen.KhoanChiDetail.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("id_khoanChi") { type = NavType.IntType },
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_khoanChi = backStackEntry.arguments?.getInt("id_khoanChi") ?: 0
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            KhoanChiDetailScreen(
                navController = navController,
                id_khoanChi = id_khoanChi,
                userId = userId,
            )
        }



        composable(
            route = Screen.AddKhoanChi.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            AddKhoanChiScreen(navController, userId)
        }

        composable(
            route = Screen.UpdateKhoanChi.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
                navArgument("id_khoanchi") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val id_khoanchi = backStackEntry.arguments?.getInt("id_khoanchi") ?: 0
            UpdateKhoanChiScreen(navController, id_khoanChi = id_khoanchi, userId = userId )
        }

        composable(
            route = Screen.ChuyenTien.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0

            ChuyenTienScreen(
                navController = navController,
                userId = userId,
            )
        }
        composable(
            route = Screen.AddTaiKhoan.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0

            AddTaiKhoanScreen(
                navController = navController,
                userId = userId,
            )
        }

        composable(
            route = Screen.LichSuChuyenTien.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0

            LichSuChuyenTienScreen(
                navController = navController,
                userId = userId,
            )
        }

        composable(
            route = Screen.ThongKeNam.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0

            ThongKeTheoNamScreen(
                navController = navController,
                userId = userId,
            )
        }

        composable(
            route = Screen.ListThuNhapTheoThang.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0

            ListThuNhapTheoThangScreen(
                navController = navController,
                userId = userId,
            )
        }

        composable(
            route = Screen.ListChiTieuTheoThang.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0

            ListChiTieuTheoThangScreen(
                navController = navController,
                userId = userId,
            )
        }

    }
}
