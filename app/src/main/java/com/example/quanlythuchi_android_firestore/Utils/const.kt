package com.example.quanlythuchi_android_firestore.Utils

import androidx.compose.ui.graphics.Color
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel
import com.example.quanlythuchi_android_firestore.domain.model.TaiKhoanModel
import com.example.quanlythuchi_android_firestore.domain.model.ThuNhapModel

const val BASE_URL = "http://chillcup.io.vn/QuanLyThuChi/public/"

val thuNhapListSample = listOf(
    ThuNhapModel(
        id = 1,
        id_nguoidung = 21,
        id_taikhoan = 1,
        so_tien = 1000000,
        ngay_tao = "2025-09-15",
        ghi_chu = "Tiền lương"
    ),
    ThuNhapModel(
        id = 1,
        id_nguoidung = 21,
        id_taikhoan = 1,
        so_tien = 1000000,
        ngay_tao = "2025-09-15",
        ghi_chu = "Tiền lương"
    ),
    ThuNhapModel(
        id = 1,
        id_nguoidung = 21,
        id_taikhoan = 1,
        so_tien = 1000000,
        ngay_tao = "2025-09-15",
        ghi_chu = "Tiền lương"
    ),
    ThuNhapModel(
        id = 1,
        id_nguoidung = 21,
        id_taikhoan = 1,
        so_tien = 1000000,
        ngay_tao = "2025-09-15",
        ghi_chu = "Tiền lương"
    ),
    ThuNhapModel(
        id = 1,
        id_nguoidung = 21,
        id_taikhoan = 1,
        so_tien = 1000000,
        ngay_tao = "2025-09-15",
        ghi_chu = "Tiền lương"
    )
)

object listKhoanChiConst {

    val listTaiKhoan = listOf(
        TaiKhoanModel(
            id = 1,
            id_nguoidung = 1,
            ten_taikhoan = "Tiền mua xe",
            so_du = 2500000,
            loai_taikhoan = 1,
            mo_ta = "Tiền để dành mua xe"
        ),
        TaiKhoanModel(
            id = 1,
            id_nguoidung = 1,
            ten_taikhoan = "Tiền mua xe",
            so_du = 2500000,
            loai_taikhoan = 1,
            mo_ta = "Tiền để dành mua xe"
        ),
        TaiKhoanModel(
            id = 1,
            id_nguoidung = 1,
            ten_taikhoan = "Tiền mua xe",
            so_du = 2500000,
            loai_taikhoan = 1,
            mo_ta = "Tiền để dành mua xe"
        ),
        TaiKhoanModel(
            id = 1,
            id_nguoidung = 1,
            ten_taikhoan = "Tiền mua xe",
            so_du = 2500000,
            loai_taikhoan = 1,
            mo_ta = "Tiền để dành mua xe"
        )
    )
    val listKhoanChi = listOf(
        KhoanChiModel(
            id = 1,
            ten_khoanchi = "Tiền ăn",
            id_nguoidung = 1,
            mausac = "red",
            ngay_batdau = "16-02-2025",
            ngay_ketthuc = "16-02-2025",
            so_tien_du_kien = 3000000,
            tong_tien_da_chi = 200000,
            emoji = "🤣"
        ),
        KhoanChiModel(
            id = 1,
            ten_khoanchi ="Tiền ăn",
            id_nguoidung = 1,
            mausac = "blue",
            ngay_batdau = "16-02-2025",
            ngay_ketthuc = "16-02-2025",
            so_tien_du_kien = 3000000,
            tong_tien_da_chi = 200000,
            emoji = "🤣"
        ),
        KhoanChiModel(
            id = 1,
            ten_khoanchi ="Tiền ăn",
            id_nguoidung = 1,
            mausac = "yellow",
            ngay_batdau = "16-02-2025",
            ngay_ketthuc = "16-02-2025",
            so_tien_du_kien = 3000000,
            tong_tien_da_chi = 200000,
            emoji = "🤣"
        ),
        KhoanChiModel(
            id = 1,
            ten_khoanchi ="Tiền ăn",
            id_nguoidung = 1,
            mausac = "green",
            ngay_batdau = "16-02-2025",
            ngay_ketthuc = "16-02-2025",
            so_tien_du_kien = 3000000,
            tong_tien_da_chi = 200000,
            emoji = "🤣"
        ),
        KhoanChiModel(
            id = 1,
            ten_khoanchi ="Tiền ăn",
            id_nguoidung = 1,
            mausac = "yellow",
            ngay_batdau = "16-02-2025",
            ngay_ketthuc = "16-02-2025",
            so_tien_du_kien = 3000000,
            tong_tien_da_chi = 200000,
            emoji = "🤣"
        ),
    )
}
