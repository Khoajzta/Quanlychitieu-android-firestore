package com.example.quanlythuchi_android_firestore.Views.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quanlythuchi_android_firestore.R
import com.example.quanlythuchi_android_firestore.domain.model.NguoiDungModel
import com.example.quanlythuchi_android_firestore.ui.components.LoadAnhTuUrl
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingBody
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusFull

@Composable
fun HeaderMain(
    modifier: Modifier,
    user: NguoiDungModel
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = PaddingBody),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .shadow(12.dp, RoundedCornerShape(RadiusFull))
                    .clip(RoundedCornerShape(RadiusFull))
                    .background(color = Color(0xFF42A7E8))
                    .padding(start = 3.dp, top = 3.dp, end = 15.dp, bottom = 3.dp)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(RadiusFull))
                            .background(color = Color.White)
                            .border(2.dp, color = Color.Green, shape = RoundedCornerShape(RadiusFull))
                            .padding(5.dp,)

                    ){
                        LoadAnhTuUrl(url = user.url_avt!!, modifier = Modifier.size(30.dp).clip(CircleShape)  )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        Text(
                            text = "Xin chào!!",
                            color = Color.White,
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        )
                        Text(
                            text = user.ten!!,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 14.sp
                        )
                    }
                }

            }

        }
//
//        Box(
//            modifier = Modifier
//                .shadow(12.dp, RoundedCornerShape(RadiusFull))
//                .clip(RoundedCornerShape(RadiusFull))
//                .background(color = Color(0xFF42A7E8))
//                .padding(5.dp)
//                .size(35.dp),
//            contentAlignment = Alignment.Center
//        ){
//            Icon(
//                imageVector = Icons.Default.Notifications,
//                contentDescription = null,
//                tint = Color.White,
//                modifier = Modifier.size(30.dp)
//            )
//        }


    }
}

@Composable
@Preview
fun HeaderMainPreview() {

}