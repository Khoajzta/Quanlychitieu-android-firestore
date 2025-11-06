package com.example.quanlythuchi_android_firestore.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quanlythuchi_android_firestore.ui.theme.AppTypography
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PaddingMedium
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusFull

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
        Row(
            modifier= modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(35.dp)
                    .shadow(12.dp, RoundedCornerShape(RadiusFull))
                    .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(RadiusFull))
                    .clickable(onClick = onBackClick)
                    .padding(PaddingMedium),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Back",
                style = AppTypography.bodyLarge
            )
        }

}


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(modifier = Modifier.height(50.dp))
}