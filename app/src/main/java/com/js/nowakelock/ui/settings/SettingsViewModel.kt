package com.js.nowakelock.ui.settings

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.beust.klaxon.Klaxon
import com.js.nowakelock.BasicApp
import com.js.nowakelock.data.repository.BackupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class SettingsViewModel(val backR: BackupRepository) : ViewModel() {


//    suspend fun getBackup(): Backup = withContext(Dispatchers.IO) {
//        return@withContext Backup().apply {
//            lalarmSt = backR.loadAlarmSt()
//            lserviceSt = backR.loadServiceSt()
//            lwakelockSt = backR.loadWakeLockSt()
//            lappInfoSt = backR.loadAppSt()
//        }
//    }

    @Throws(IOException::class)
    suspend fun getString(uri: Uri): String = withContext(Dispatchers.IO) {
        val stringBuilder = StringBuilder()
        BasicApp.context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return@withContext stringBuilder.toString()
    }


    suspend fun btoJ(backup: Backup): String = withContext(Dispatchers.Default) {
        return@withContext Klaxon().toJsonString(backup)
    }

    suspend fun jtoB(json: String): Backup? = withContext(Dispatchers.Default) {
        return@withContext Klaxon().parse<Backup>(json)
    }
}