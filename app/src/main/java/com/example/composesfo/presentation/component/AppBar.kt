package com.example.composesfo.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TopBarTitle(
    textOne: String,
    textTwo: String
) {
    Column {
        Text(
            text = textOne,
            fontStyle = FontStyle.Italic,
            fontSize = 30.sp
        )

        Text(
            text = textTwo,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = 30.sp
        )
    }
}