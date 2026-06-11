package com.droid.letsbuy.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class Prefs(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MAIN", Context.MODE_PRIVATE)

    var themeDark: Boolean
        get() = sharedPreferences.getBoolean("themeDark", false)
        set(value) = sharedPreferences.edit { putBoolean("themeDark", value) }
}