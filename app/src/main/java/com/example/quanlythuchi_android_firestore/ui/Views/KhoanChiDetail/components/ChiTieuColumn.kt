package com.example.quanlythuchi_android_firestore.ui.Views.KhoanChiDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.quanlythuchi_android_firestore.domain.model.ChiTieuModel
import com.example.quanlythuchi_android_firestore.ui.components.CardChiTieu
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceMedium

@Composable
fun ListChiTieuColumn(
    listChiTieu: List<ChiTieuModel>,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(horizontal = PaddingBody),
        verticalArrangement = Arrangement.spacedBy(SpaceMedium)
    ) {
        for (i in listChiTieu.indices) {
            CardChiTieu(
                chitieu = listChiTieu[i],
                modifier = Modifier
            )
        }
    }
}