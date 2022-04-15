package com.example.composesfo.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.composesfo.R
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

@Composable
fun TextFieldWithNoIcon(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    boolean: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
    ),
    modifier: Modifier
) {
    val focusRequester = FocusRequester()
    var focusState by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = { onTextChange(it) },
        label = { Text(text = label) },
        singleLine = boolean,
        trailingIcon = {
            if (focusState) {
                IconButton(
                    onClick = { onTextChange("") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        null
                    )
                }
            }
        },
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                focusState = it.isFocused
            }
    )
}

@Composable
fun TextFieldWithIcon(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    boolean: Boolean = true,
    painter: Painter = painterResource(id = R.drawable.ic_clear),
    iconColor: Color = Color.Transparent,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
    )
) {
    val focusRequester = FocusRequester()
    var focusState by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = { onTextChange(it) },
        label = { Text(text = label) },
        singleLine = boolean,
        leadingIcon = {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = iconColor
            )
        },
        trailingIcon = {
            if (focusState) {
                IconButton(
                    onClick = { onTextChange("") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        null
                    )
                }
            }
        },
        keyboardOptions = keyboardOptions,
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .onFocusChanged {
                focusState = it.isFocused
            },
    )
}

@Composable
fun TextFieldWithShowPasswordIcon(
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    painter: Painter = painterResource(id = R.drawable.ic_clear),
    iconColor: Color = Color.Transparent,
    showPassword: Boolean,
    showPasswordChange: (Boolean) -> Unit,
    boolean: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
    )
) {
    val focusRequester = FocusRequester()
    var focusState by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = { onTextChange(it) },
        label = { Text(text = label) },
        singleLine = boolean,
        leadingIcon = {
            Icon(
                painter = painter,
                contentDescription = null,
                tint = iconColor
            )
        },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            if (focusState) {
                if (showPassword) {
                    IconButton(
                        onClick = { showPasswordChange(false) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_visibility),
                            null
                        )
                    }
                } else {
                    IconButton(
                        onClick = { showPasswordChange(true) }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_visibility_off),
                            null
                        )
                    }
                }
            }
        },
        keyboardOptions = keyboardOptions,
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth()
            .onFocusChanged {
                focusState = it.isFocused
            },
    )
}

@SuppressLint("SimpleDateFormat")
fun getDate(format: String): String {
    val date = Calendar.getInstance()
    val currentDateID = java.text.SimpleDateFormat(format)
    return currentDateID.format(date.time)
}

@SuppressLint("SimpleDateFormat")
fun getTime(format: String): String {
    val date = Calendar.getInstance()
    val currentDateID = java.text.SimpleDateFormat(format)
    return currentDateID.format(date.time)
}

