package com.example.minigithub

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.minigithub.data.FavoriteRepository
import com.example.minigithub.ui.detail.DetailViewModel
import com.example.minigithub.ui.favorite.FavoriteViewModel
import com.example.minigithub.ui.main.MainViewModel
import com.example.minigithub.ui.theme.SettingPreference
import com.example.minigithub.ui.theme.ThemeViewModel

// ViewModelFactory adalah kelas yang digunakan untuk membuat ViewModel dengan dependensi.
class ViewModelFactory private constructor(
    private val favoriteRepository: FavoriteRepository,
    private val settingPreference: SettingPreference
) : ViewModelProvider.NewInstanceFactory() {

    // Metode untuk membuat instance ViewModel sesuai dengan tipe kelas yang diberikan.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // Jika tipe kelas adalah DetailViewModel, inisialisasi dengan favoriteRepository.
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(
                favoriteRepository
            ) as T

            // Jika tipe kelas adalah MainViewModel, inisialisasi dengan settingPreference.
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(
                settingPreference
            ) as T

            // Jika tipe kelas adalah FavoriteViewModel, inisialisasi dengan favoriteRepository.
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(
                favoriteRepository
            ) as T

            // Jika tipe kelas adalah ThemeViewModel, inisialisasi dengan settingPreference.
            modelClass.isAssignableFrom(ThemeViewModel::class.java) -> ThemeViewModel(
                settingPreference
            ) as T

            // Jika tipe kelas tidak dikenal, lemparkan pengecualian.
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        // Metode untuk mendapatkan instance tunggal dari ViewModelFactory.
        // Jika belum ada instance, maka buat instance baru dengan dependensi yang diberikan.
        fun getInstance(context: Context) = instance
            ?: synchronized(this) {
                instance
                    ?: ViewModelFactory(
                        DependencyProvider .provideFavoriteRepository(context),
                        DependencyProvider .provideSettingPreference(context)
                    )
                        .also { instance = it }
            }
    }
}
