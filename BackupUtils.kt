package com.jigong.helper

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

object BackupUtils {
    private fun backupFile(ctx: Context, index: Int): File =
        File(ctx.filesDir, "backup_$index.json")

    fun autoBackupOnStartOrClose(ctx: Context) {
        if (!SpUtil.hasDataChanged) return
        GlobalScope.launch(Dispatchers.IO) {
            val list = AppDatabase.instance.workRecordDao().getAllRecords()
            val json = Gson().toJson(list)
            backupFile(ctx, 1).copyTo(backupFile(ctx, 2), overwrite = true)
            backupFile(ctx, 0).copyTo(backupFile(ctx, 1), overwrite = true)
            backupFile(ctx, 0).writeText(json)
            SpUtil.hasDataChanged = false
        }
    }

    fun manualBackup(ctx: Context) {
        SpUtil.hasDataChanged = true
        autoBackupOnStartOrClose(ctx)
    }
}
