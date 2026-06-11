package com.droid.letsbuy.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {
    var isDarkThemeEnabled = mutableStateOf(false)
        private set

    fun setDarkTheme(isDarkTheme: Boolean) {
        isDarkThemeEnabled.value = isDarkTheme
    }

}