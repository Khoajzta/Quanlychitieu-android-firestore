package com.example.quanlythuchi_android_firestore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ButtonBack(
    navController: NavController,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Gray,
    iconColor: Color = Color.White
) {
    Box(
        modifier = modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(backgroundColor.copy(0.2f))
            .clickable(
            ) {
                navController.popBackStack()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = "Back",
            tint = iconColor,
            modifier = Modifier.padding(end = 3.dp).size(15.dp).align(Alignment.Center)
        )
    }
}





@Composable
@Preview
fun ButtnBackPreview(){
    var navController = rememberNavController()
    ButtonBack(navController)

}