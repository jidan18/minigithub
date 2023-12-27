package com.example.minigithub

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.minigithub.data.dbFavoriteUser
import com.example.minigithub.data.FavoriteRepository
import com.example.minigithub.remote.ApiConfig
import com.example.minigithub.ui.theme.SettingPreference

// Ekstensi untuk mengakses DataStore preferensi aplikasi
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

/**
 * Kelas `DependencyProvider` menyediakan metode-metode untuk menginjeksi dependensi dalam aplikasi.
 * Ini termasuk penyediaan repositori favorit dan preferensi tema.
 */
object DependencyProvider {

    /**
     * Metode untuk menyediakan instance [FavoriteRepository] dengan menginjeksi dependensi yang diperlukan.
     *
     * @param context Konteks aplikasi.
     * @return Instance [FavoriteRepository].
     */
    fun provideFavoriteRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val favoriteDatabase = dbFavoriteUser.getDatabase(context)
        val favDao = favoriteDatabase.favDao()
        return FavoriteRepository(apiService, favDao)
    }

    /**
     * Metode untuk menyediakan instance [SettingPreference] dengan menginjeksi DataStore preferensi.
     *
     * @param context Konteks aplikasi.
     * @return Instance [SettingPreference].
     */
    fun provideSettingPreference(context: Context): SettingPreference {
        return SettingPreference.getInstance(context.dataStore)
    }
}
