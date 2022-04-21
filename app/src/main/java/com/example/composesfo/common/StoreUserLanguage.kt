package com.example.composesfo.common

import android.content.Context
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreUserLanguage(private val context: Context) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userLanguage")
        val USER_LANGUAGE_KEY = stringPreferencesKey("user_language")
    }

    private val currentSystemLocale = ConfigurationCompat
        .getLocales(Resources.getSystem().configuration)
        .get(0).displayLanguage

    //get the saved email
    val getLanguage: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_LANGUAGE_KEY] ?: currentSystemLocale
        }

    //save email into datastore
    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_LANGUAGE_KEY] = language
        }
    }


}