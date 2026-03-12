package com.jigong.helper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkRecordDao {
    @Query("SELECT * FROM work_record ORDER BY createTime DESC")
    fun getAllRecords(): Flow<List<WorkRecord>>

    @Insert
    suspend fun insert(record: WorkRecord)

    @Update
    suspend fun update(record: WorkRecord)

    @Query("DELETE FROM work_record")
    suspend fun clearAll()
}
