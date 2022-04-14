package com.example.composesfo.presentation.component

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import com.example.composesfo.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

inline fun Modifier.noRippleClickable(crossinline onClick: ()->Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Composable
fun HeaderImage() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1.25f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Image(
            painter = painterResource(id = R.drawable.wujiak),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@SuppressLint("SimpleDateFormat")
fun getDate(): String {
    val date = Calendar.getInstance()
    val currentDateID = java.text.SimpleDateFormat("MMddyyyy")
    return currentDateID.format(date.time)
}

@SuppressLint("SimpleDateFormat")
fun getTime(): String {
    val date = Calendar.getInstance()
    val currentDateID = java.text.SimpleDateFormat("mmHHss")
    return currentDateID.format(date.time)
}


