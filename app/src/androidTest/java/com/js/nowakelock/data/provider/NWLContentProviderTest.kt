package com.js.nowakelock.data.provider

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.test.core.app.ApplicationProvider
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.base.WLUtil
import com.js.nowakelock.data.TestData
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.entity.WakeLock_st
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NWLContentProviderTest {

    private var mContentResolver: ContentResolver? = null
    private lateinit var db: AppDatabase

    private val authority = "com.js.nowakelock"

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = AppDatabase.getInstance(context)
        mContentResolver = context.contentResolver
    }

    @Test
    fun callTest() {
        val method = "test"
        val url = Uri.parse("content://$authority")

        val extras = Bundle()
        extras.putString("Test", "Test")

        val bundle = mContentResolver?.call(url, method, null, extras)
        if (bundle != null) {
            LogUtil.d("test1", bundle.toString())
        }
        val tmp2 = bundle!!.getSerializable("test") as HashMap<String, WakeLock_st>

        assertEquals(bundle.getString("Test"), "Test")
        LogUtil.d("test1", tmp2.toString())
        assertEquals(tmp2["test"]?.flag, false)
    }

    @Test
    fun callTest2() {
        val method = "test"
        val url = Uri.parse("content://$authority")

        val extras = Bundle()
        extras.putParcelable("Test", com.js.nowakelock.test.Test("test1", "package1", 2, 2, 2, 2))

        val bundle = mContentResolver?.call(url, method, null, extras)
        if (bundle != null) {
            LogUtil.d("test1", bundle.toString())
        }

        val tmp2 = bundle!!.getParcelable<com.js.nowakelock.test.Test?>("Test")

        LogUtil.d("test12", tmp2.toString())
//        assertEquals(bundle.getString("Test"), "Test")
//        assertEquals(tmp2["test"]?.flag, false)
    }

    @Test
    fun testwl() {
        val url = Uri.parse("content://${authority}")
        try {
            val tmp = mContentResolver?.call(url, "wlStsHM", null, Bundle())
            LogUtil.d("Xposed.NoWakeLock", "Bundle4 ${tmp}")
        } catch (e: Exception) {
            LogUtil.d("Xposed.NoWakeLock", "Bundle4 err :${e}")
        }
    }

    @Test
    fun insert() {
        val method = "saveWL"
        val url = Uri.parse("content://$authority")

        val extras = WLUtil.getBundle(TestData.wakeLocks[0])
        mContentResolver?.call(url, method, null, extras)
    }
}

