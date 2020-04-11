package com.js.nowakelock.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.entity.WakeLock

class NWLContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.js.nowakelock"

//        const val test = 0
//        const val flag = 1
//        const val upCount = 2
//        const val upBlockCount = 3
//
//        const val PackageName = "PackageName"
//        const val WakelockName = "WakelockName"
//
//        val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
//            addURI(AUTHORITY, "flag", flag)
//            addURI(AUTHORITY, "upCount", upCount)
//            addURI(AUTHORITY, "upBlockCount", upBlockCount)
//            addURI(AUTHORITY, "test", test)
//        }

    }

//    private lateinit var db: AppDatabase


    override fun onCreate(): Boolean {
//        if (context != null) {
//            db = AppDatabase.getInstance(context!!)
//        }
        return true
    }


    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
//        if (selection == null) throw NullPointerException()
//        if (context == null) {
//            return null
//        }
//        //获取url匹配代码
//        when (sUriMatcher.match(uri)) {
//            flag -> return db.wakeLockDao().loadFlag(selection)
//            else -> throw IllegalArgumentException("Unknown URI: $uri")
//        }
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
//        if (context == null && values == null) {
//            return null
//        }
//        val wN = values!!.getAsString(WakelockName)
//        val pN = values.getAsString(PackageName)
//        val code = getStatus(pN, wN)
//        //获取url匹配代码
////        GlobalScope.launch(Dispatchers.IO) {
//        when (sUriMatcher.match(uri)) {
//            upCount -> {
//                when (code) {
////                        0 -> throw IllegalArgumentException("$pN: App not install")
//                    1 -> {
//                        db.wakeLockDao().insert(WakeLock(pN, wN, count = 1, blockCount = 0))
//                    }
//                    2 -> {
//                        db.wakeLockDao().upCount(wN)
//                        db.wakeLockDao().upCountP(pN)
//                    }
////                    else -> throw IllegalArgumentException("Unknown URI: $uri")
//                }
//            }
//            upBlockCount -> {
//                when (code) {
////                        0 -> throw IllegalArgumentException("$pN: App not install")
//                    1 -> {
//                        db.wakeLockDao().insert(WakeLock(pN, wN, count = 1, blockCount = 1))
//                    }
//                    2 -> {
//                        db.wakeLockDao().upCount(wN)
//                        db.wakeLockDao().upCountP(pN)
//                        db.wakeLockDao().upBlockCount(wN)
//                        db.wakeLockDao().upBlockCountP(pN)
//                    }
////                        else -> throw IllegalArgumentException("Unknown URI: $uri")
//                }
//            }
//            test -> Uri.parse("test $pN $wN")
////            else -> {
////                throw IllegalArgumentException("Unknown URI: $uri")
////            }
////            }
//        }
        return uri
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        //no need handler
        if (extras == null) {
            return null
        }
        return context?.let { ProviderHandler.getInstance(it).getMethod(method, extras) }
    }

//        val bundle = Bundle()
//        bundle.putBoolean(method, true)
//        return super.call(method, arg, extras)
//        LogUtil.d("test1", method)
//        LogUtil.d("test1", mService?.serviceModel!!.TAG)
//        return bundle


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
