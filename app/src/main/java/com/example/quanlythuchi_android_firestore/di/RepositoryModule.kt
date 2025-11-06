package com.example.quanlythuchi_android_firestore.di


import com.example.quanlythuchi_android_firestore.data.respository.AuthRepositoryImpl
import com.example.quanlythuchi_android_firestore.data.respository.ChiTieuRepositoryImpl
import com.example.quanlythuchi_android_firestore.data.respository.ChuyenTienRepositoryImpl
import com.example.quanlythuchi_android_firestore.data.respository.KhoanChiRepositoryImpl
import com.example.quanlythuchi_android_firestore.data.respository.NguoiDungRepositoryImpl
import com.example.quanlythuchi_android_firestore.data.respository.TaiKhoanRepositoryImpl
import com.example.quanlythuchi_android_firestore.data.respository.ThuNhapRepositoryImpl
import com.example.quanlythuchi_android_firestore.domain.respository.AuthRepository
import com.example.quanlythuchi_android_firestore.domain.respository.ChiTieuRespository
import com.example.quanlythuchi_android_firestore.domain.respository.ChuyenTienRepository
import com.example.quanlythuchi_android_firestore.domain.respository.KhoanChiRepository
import com.example.quanlythuchi_android_firestore.domain.respository.NguoiDungRepository
import com.example.quanlythuchi_android_firestore.domain.respository.TaiKhoanRepository
import com.example.quanlythuchi_android_firestore.domain.respository.ThuNhapRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindKhoanChiRepository(
        impl: KhoanChiRepositoryImpl
    ): KhoanChiRepository

    @Binds
    @Singleton
    abstract fun bindNguoiDungRepository(
        impl: NguoiDungRepositoryImpl
    ): NguoiDungRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTaiKhoanRepository(
        impl: TaiKhoanRepositoryImpl
    ): TaiKhoanRepository

    @Binds
    @Singleton
    abstract fun bindChiTieuRepository(
        impl: ChiTieuRepositoryImpl
    ): ChiTieuRespository

    @Binds
    @Singleton
    abstract fun bindThuNhapRepository(
        impl: ThuNhapRepositoryImpl
    ): ThuNhapRepository

    @Binds
    @Singleton
    abstract fun bindChuyenTienRepository(
        impl: ChuyenTienRepositoryImpl
    ): ChuyenTienRepository
}
