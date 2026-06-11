package com.droid.letsbuy.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ThemeViewModel : ViewModel() {
    private val _isDarkThemeEnabled = MutableStateFlow(false)
    val isDarkThemeEnabled = _isDarkThemeEnabled

    fun setTheme(value: Boolean) {
        _isDarkThemeEnabled.value = value
    }


}