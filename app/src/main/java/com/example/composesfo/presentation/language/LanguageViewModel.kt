package com.example.composesfo.presentation.language

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.composesfo.common.CurrentUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(

) : ViewModel() {
    var openDialog by mutableStateOf(false)
        private set

    var selectedOption by mutableStateOf(CurrentUserState.language)
        private set

    fun onOpenDialogChange(boolean: Boolean) {
        openDialog = boolean
    }

    fun onOptionSelected(text: String) {
        selectedOption = text
    }
}