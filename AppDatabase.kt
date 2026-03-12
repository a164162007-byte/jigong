package com.jigong.helper

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WorkRecord::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workRecordDao(): WorkRecordDao

    companion object {
        val instance by lazy {
            Room.databaseBuilder(
                MyApp.instance,
                AppDatabase::class.java,
                "work_db"
            ).enableWriteAheadLogging().build()
        }
    }
}
