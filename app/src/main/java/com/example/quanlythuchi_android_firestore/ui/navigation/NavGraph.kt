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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
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
                navArgument("id_khoanChi") { type = NavType.StringType },
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id_khoanChi = backStackEntry.arguments?.getString("id_khoanChi") ?: ""
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AddKhoanChiScreen(navController, userId)
        }

        composable(
            route = Screen.UpdateKhoanChi.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType },
                navArgument("id_khoanchi") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val id_khoanchi = backStackEntry.arguments?.getString("id_khoanchi") ?: ""
            UpdateKhoanChiScreen(
                navController,
                id_khoanChi = id_khoanchi,
                userId = userId
            )
        }

        composable(
            route = Screen.ChuyenTien.route,
            enterTransition = truotVaoTuPhai(),
            exitTransition = truotRaSangTrai(),
            popEnterTransition = truotVaoTuTrai(),
            popExitTransition = truotRaSangPhai(),
            arguments = listOf(
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

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
                navArgument("userId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""

            ListChiTieuTheoThangScreen(
                navController = navController,
                userId = userId,
            )
        }

    }
}
