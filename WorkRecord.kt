package com.jigong.helper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "work_record")
data class WorkRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: Int,
    val date: String,
    val startTime: String? = null,
    val endTime: String? = null,
    val workHours: Double = 0.0,
    val location: String,
    val createTime: Long = System.currentTimeMillis()
)
