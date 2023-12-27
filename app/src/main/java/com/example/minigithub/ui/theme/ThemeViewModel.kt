package com.example.minigithub.ui.theme

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ThemeViewModel(private val settingsPreference: SettingPreference) : ViewModel() {

    val isDarkModeActiveLiveData: LiveData<Boolean> by lazy {
        settingsPreference.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActiveLiveData: Boolean) {
        viewModelScope.launch {
            settingsPreference.saveThemeSetting(isDarkModeActiveLiveData)
        }
    }
}
