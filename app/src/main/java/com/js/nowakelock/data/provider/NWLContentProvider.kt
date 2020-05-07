package com.js.nowakelock.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.entity.WakeLock

class NWLContentProvider : ContentProvider() {


    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return uri
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        //no need handler
        if (extras == null) {
            return null
        }
        return context?.let { ProviderHandle.getInstance(it).getMethod(method, extras) }
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

//    private fun getStatus(pN: String, wN: String): Int {
////        var t1 = db.wakeLockDao().loadPackageName(pN)
////        var t2 = db.wakeLockDao().loadWakelockName(wN)
////
////        LogUtil.d("test1", t1.toString())
////        LogUtil.d("test1",t2)
//
//        if (db.wakeLockDao().loadPackageName(pN) == null) {
//            return 0
//        } else {
//            if (db.wakeLockDao().loadWakelockName(wN) == null) {
//                return 1
//            } else {
//                return 2
//            }
//        }
//    }
}
