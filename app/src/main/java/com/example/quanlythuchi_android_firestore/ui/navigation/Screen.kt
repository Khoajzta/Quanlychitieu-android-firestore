package com.example.quanlythuchi_android_firestore.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Home : Screen("home/{userId}") {
        fun createRoute(userId: Int) = "home/$userId"
    }
    object Profile : Screen("profile/{userId}"){
        fun createRoute(userId: Int) = "profile/$userId"
    }
    object Trade : Screen("trade/{userId}"){
        fun createRoute(userId: Int) = "trade/$userId"
    }

    object NganSach : Screen("nganSach/{userId}"){
        fun createRoute(userId: Int) = "nganSach/$userId"
    }
    object AddTrade : Screen("add_trade/{userId}"){
        fun createRoute(userId: Int) = "add_trade/$userId"
    }
    object ListKhoanChi : Screen("listkhoanchi/{userId}"){
        fun createRoute(userId: Int) = "listkhoanchi/$userId"
    }
    object KhoanChiDetail : Screen("khoanchi_detail/{id_khoanChi}/{userId}"){
        fun createRoute(id_khoanChi: Int , userId: Int) = "khoanchi_detail/$id_khoanChi/$userId"
    }
    object AddKhoanChi : Screen("addkhoanchi/{userId}"){
        fun createRoute(userId: Int) = "addkhoanchi/$userId"
    }

    object UpdateKhoanChi : Screen("updatekhoanchi/{userId}/{id_khoanchi}"){
        fun createRoute(userId: Int, id_khoanchi: Int) = "updatekhoanchi/$userId/$id_khoanchi"
    }

    object ChuyenTien : Screen("chuyen_tien/{userId}"){
        fun createRoute(userId: Int) = "chuyen_tien/$userId"
    }

    object AddTaiKhoan : Screen("add_taikhoan/{userId}"){
        fun createRoute(userId: Int) = "add_taikhoan/$userId"
    }

    object LichSuChuyenTien : Screen("lichsuchuyentien/{userId}"){
        fun createRoute(userId: Int) = "lichsuchuyentien/$userId"
    }

    object ThongKeNam : Screen("thong_ke_nam/{userId}"){
        fun createRoute(userId: Int) = "thong_ke_nam/$userId"
    }

    object ListThuNhapTheoThang : Screen("listThuNhaptheoThang/{userId}"){
        fun createRoute(userId: Int) = "listThuNhaptheoThang/$userId"
    }

    object ListChiTieuTheoThang : Screen("listChiTieutheoThang/{userId}"){
        fun createRoute(userId: Int) = "listChiTieutheoThang/$userId"
    }

}