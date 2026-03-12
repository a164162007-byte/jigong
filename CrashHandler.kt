package com.jigong.helper

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import kotlin.system.exitProcess

object CrashHandler : Thread.UncaughtExceptionHandler {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(appContext, "程序异常，即将重启", Toast.LENGTH_SHORT).show()
        }
        try {
            Thread.sleep(800)
        } catch (ex: Exception) {
        }
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(1)
    }
}
