package com.example.minigithub.ui.theme

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Kelas `SettingPreference` digunakan untuk mengelola preferensi terkait tema aplikasi.
 * Ini menggunakan DataStore Preferences untuk menyimpan preferensi tema seperti mode gelap atau terang.
 *
 * @property dataStore DataStore yang akan digunakan untuk menyimpan preferensi.
 */
class SettingPreference private constructor(private val dataStore: DataStore<Preferences>) {

    // Kunci preferensi untuk pengaturan tema
    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    /**
     * Mengambil preferensi pengaturan tema sebagai [Flow] berdasarkan dataStore.
     *
     * @return [Flow] yang menghasilkan nilai boolean, mewakili apakah mode gelap aktif atau tidak.
     */
    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    /**
     * Menyimpan pengaturan tema yang dipilih ke DataStore.
     *
     * @param isDarkModeActive Boolean yang menentukan apakah mode gelap aktif atau tidak.
     */
    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreference? = null

        /**
         * Mendapatkan instance tunggal dari [SettingPreference].
         * Jika instance belum ada, maka akan dibuat satu instance tunggal.
         *
         * @param dataStore DataStore yang akan digunakan untuk menyimpan preferensi.
         * @return Instance tunggal dari [SettingPreference].
         */
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreference {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SettingPreference(dataStore).also { INSTANCE = it }
            }
        }
    }
}
