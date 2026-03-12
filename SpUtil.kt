package com.jigong.helper

import android.content.Context

object SpUtil {
    private const val NAME = "config"
    private val sp by lazy { MyApp.instance.getSharedPreferences(NAME, Context.MODE_PRIVATE) }

    var normalHours: Double
        get() = sp.getFloat("normalHours", 8f).toDouble()
        set(v) = sp.edit().putFloat("normalHours", v.toFloat()).apply()

    var overtimeConvertHours: Double
        get() = sp.getFloat("overtimeConvertHours", 8f).toDouble()
        set(v) = sp.edit().putFloat("overtimeConvertHours", v.toFloat()).apply()

    var mealPrice: Int
        get() = sp.getInt("mealPrice", 15)
        set(v) = sp.edit().putInt("mealPrice", v).apply()

    var backupDays: Int
        get() = sp.getInt("backupDays", 7)
        set(v) = sp.edit().putInt("backupDays", v).apply()

    var lastBackupTime: Long
        get() = sp.getLong("lastBackupTime", 0)
        set(v) = sp.edit().putLong("lastBackupTime", v).apply()

    var hasDataChanged: Boolean
        get() = sp.getBoolean("hasDataChanged", false)
        set(v) = sp.edit().putBoolean("hasDataChanged", v).apply()
}
