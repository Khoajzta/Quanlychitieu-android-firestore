package com.example.quanlythuchi_android_firestore.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.PadingExtraSmall
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusFull
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.RadiusLarge
import com.example.quanlythuchi_android_firestore.ui.theme.Dimens.SpaceLarge
import com.example.quanlythuchi_android_firestore.ui.theme.PrimaryColor
import com.example.quanlythuchi_android_firestore.ui.theme.QuanLyChiTieuTheme


@Composable
fun CusTomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: (@Composable (() -> Unit))? = null,
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    readOnly: Boolean = false
) {

    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(RadiusLarge),
                clip = false
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(RadiusLarge)
            )
            .clickable { focusRequester.requestFocus() }
            .padding(PadingExtraSmall)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = leadingIcon,
            modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
            placeholder = {
                Text(
                    placeholder,
                    color = Color.Gray
                )
            },
            shape = RoundedCornerShape(RadiusFull),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = PrimaryColor,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            readOnly = readOnly,

        )
    }
}



@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit
) {
    // trạng thái ẩn/hiện mật khẩu
    var passwordVisible by remember { mutableStateOf(false) }


    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(RadiusFull),
                clip = false
            )

            .padding(PadingExtraSmall)
    ){

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,

            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    "Mật khẩu"
                )
            },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },

            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Key,
                    contentDescription = null
                )
            },

            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Default.Visibility
                else
                    Icons.Filled.VisibilityOff

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            },

            shape = RoundedCornerShape(RadiusFull),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            )
        )
    }

}



@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    QuanLyChiTieuTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SpaceLarge),

        ) {
            CusTomTextField(
                value = "",
                onValueChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                placeholder = "Tìm kiếm"
            )
            CusTomTextField(
                value = "",
                onValueChange = {},
                placeholder = "Email",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null
                    )
                },
            )

            PasswordTextField(
                modifier = Modifier,
                value = "",
                onValueChange = {}
            )


        }
    }
}




