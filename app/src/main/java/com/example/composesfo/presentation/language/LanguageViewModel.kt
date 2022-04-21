package com.example.composesfo.presentation.language

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesfo.common.StoreUserLanguage
import com.example.composesfo.presentation.component.LocalazyWrapperListener
import com.localazy.android.Localazy
import com.localazy.android.LocalazyLocale
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(

) : ViewModel() {
    var openDialog by mutableStateOf(false)
        private set

    var locales by mutableStateOf(listOf<LocalazyLocale>())
        private set

    private val listener = LocalazyWrapperListener {
        viewModelScope.launch {
            update()
        }
    }

    init {
        Localazy.setListener(listener)
        update()
    }

    private fun update() {
        locales = Localazy.getLocales() ?: emptyList()
    }

    fun saveLanguage(dataStore: StoreUserLanguage, text: String) {
        viewModelScope.launch {
            dataStore.saveLanguage(text)
        }
    }

    fun onOpenDialogChange(boolean: Boolean) {
        openDialog = boolean
    }
}