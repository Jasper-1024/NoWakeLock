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
        LogUtil.d("Xposed.NoWakeLock", "call $method, $extras")
        return context?.let { ProviderHandler.getInstance(it).getMethod(method, extras) }
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

}
