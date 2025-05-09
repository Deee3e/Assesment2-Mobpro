package com.desijihanf607062300071.assesment2.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingDataStore(private val context: Context) {
    private val dataStore: DataStore<Preferences> = context.applicationContext.dataStore
    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
        private const val PREFERENCES_NAME = "setting_preference"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = PREFERENCES_NAME
        )
    }

    val layoutFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LIST] ?: true
    }

    suspend fun saveLayout(isList: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LIST] = isList
        }
    }
}
