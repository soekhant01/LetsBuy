package com.droid.letsbuy

import android.app.Application
import com.droid.letsbuy.utils.Prefs

class MyApp : Application() {

    lateinit var prefs: Prefs
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = Prefs(applicationContext)
    }

    companion object Companion {
        lateinit var instance: MyApp
            private set
    }
}