package com.droid.letsbuy.Application

import android.app.Application
import com.droid.letsbuy.utils.Prefs


val prefs: Prefs by lazy {
    App.prefs!!
}

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = Prefs(applicationContext)
    }

    companion object {
        var prefs: Prefs? = null
        lateinit var instance: App
            private set

    }
}