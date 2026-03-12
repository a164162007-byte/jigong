package com.jigong.helper

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class MyApp : Application() {
    companion object {
        lateinit var instance: MyApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        CrashHandler.init(this)
    }
}
