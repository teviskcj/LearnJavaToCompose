package com.example.composesfo.presentation.translator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TranslatorViewModel @Inject constructor(

) : ViewModel() {
    var inputTranslator by mutableStateOf("")
        private set

    fun onInputTranslatorChange(text: String) {
        inputTranslator = text
    }
}