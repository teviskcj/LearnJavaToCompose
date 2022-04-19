package com.example.composesfo.presentation.component

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import com.example.composesfo.R

class ExposedDropMenuStateHolder {
    val items = listOf(
        "What is your date of birth?",
        "What was your favorite school teacher’s name?",
        "What’s your favorite movie?",
        "What was your first car?",
        "What city were you born in?",
        "In what city or town did your parents meet?",
        "What is the name of your favorite pet?",
        "What is the name of your first school?",
        "What is your favorite food?",
        "What is the first name of your first boyfriend/girlfriend?",
        "What is your favorite sports team?"
    )

    var enabled by mutableStateOf(false)
    var value by mutableStateOf(items[0])
    var selectedIndex by mutableStateOf(-1)
    var size by mutableStateOf(Size.Zero)
    val icon:Int
    @Composable get() = if (enabled) {
        R.drawable.ic_arrow_drop_up
    } else {
        R.drawable.ic_arrow_drop_down
    }

    fun onEnabledChange(boolean: Boolean) {
        enabled = boolean
    }

    fun onSelectedIndexChange(index: Int) {
        selectedIndex = index
        value = items[selectedIndex]
    }

    fun onSizeChange(newSize: Size) {
        size = newSize
    }
}

@Composable
fun rememberExposedMenuStateHolder() = remember() {
    ExposedDropMenuStateHolder()
}