package com.example.quanlythuchi_android_firestore.ui.Views.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quanlythuchi_android_firestore.Components.CardKhoanChi
import com.example.quanlythuchi_android_firestore.Utils.listKhoanChiConst.listKhoanChi
import com.example.quanlythuchi_android_firestore.domain.model.KhoanChiModel


@Composable
fun KhoanChiColumn(
    listKhoanChi: List<KhoanChiModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(listKhoanChi.isNullOrEmpty()){
            Text(
                text = "Chưa có khoản chi nào"
            )
        }else{
            for (i in listKhoanChi.indices) {
                CardKhoanChi(
                    item = listKhoanChi[i],
                    modifier = Modifier
                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewCardKhoanChi() {
    KhoanChiColumn(
        listKhoanChi,
    )

}