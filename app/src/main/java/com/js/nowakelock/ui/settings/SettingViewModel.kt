package com.js.nowakelock.ui.settings

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beust.klaxon.Klaxon
import com.js.nowakelock.BasicApp
import com.js.nowakelock.R
import com.js.nowakelock.data.repository.backup.Backup
import com.js.nowakelock.data.repository.backup.BackupRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*

class SettingViewModel(private val backupRepo: BackupRepo) : ViewModel() {


    fun backup(uri: Uri) {
        viewModelScope.launch(Dispatchers.Default) {
            val back = getBackup()
//            LogUtil.d("test1", "$back")
            saveFile(uri, btoJ(back))
            toast(BasicApp.context.getString(R.string.backup))
        }
    }

    fun restore(uri: Uri) {
        viewModelScope.launch(Dispatchers.Default) {
            val str = getString(uri)
            if (str != "[]") {
                jtoB(str)?.let {
                    backupRepo.restoreBackup(it)
                }
            }
            toast(BasicApp.context.getString(R.string.restore))
        }
    }


    private suspend fun getBackup(): Backup = withContext(Dispatchers.IO) {
        return@withContext backupRepo.getBackup()
    }

    //@Throws(IOException::class)
    private suspend fun getString(uri: Uri): String = withContext(Dispatchers.IO) {
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

    private suspend fun saveFile(uri: Uri, str: String) = withContext(Dispatchers.IO) {
        try {
            BasicApp.context.contentResolver.openFileDescriptor(uri, "w")?.use { it ->
                // use{} lets the document provider know you're done by automatically closing the stream
                FileOutputStream(it.fileDescriptor).use { file ->
                    file.write(
                        str.toByteArray()
                    )
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private suspend fun btoJ(backup: Backup): String = withContext(Dispatchers.Default) {
        return@withContext Klaxon().toJsonString(backup)
    }

    private suspend fun jtoB(json: String): Backup? = withContext(Dispatchers.Default) {
        return@withContext Klaxon().parse<Backup>(json)
    }

    private suspend fun toast(str: String) = withContext(Dispatchers.Main) {
        Toast.makeText(BasicApp.context, str, Toast.LENGTH_LONG).show()
    }
}