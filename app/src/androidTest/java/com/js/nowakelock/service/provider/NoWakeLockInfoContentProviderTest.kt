package com.js.nowakelock.service.provider

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.js.nowakelock.base.LogUtil
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class NoWakeLockInfoContentProviderTest {
    private var mContentResolver: ContentResolver? = null
    private val authority = "com.js.nowakelock"

    private val packageName = "PackageName"
    private val wakelockName = "WakelockName"
    private val flaG = "Flag"

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mContentResolver = context.contentResolver
    }

    @Test
    fun testNull() {
        val method = "test"
        val url = Uri.parse("content://$authority")
        val bundle = mContentResolver?.call(url, method, null, null)
        assertEquals(bundle, null)
    }

    @Test
    fun testTest() {
        val method = "test"
        val url = Uri.parse("content://$authority")

        val extras = Bundle()
        extras.let {
            it.putString(packageName, "testpn")
            it.putString(wakelockName, "testwn")
            it.putBoolean(flaG, false)
        }

        val bundle = mContentResolver?.call(url, method, null, extras)
        if (bundle != null) {
            LogUtil.d("test1", bundle.toString())
        }

        assertEquals(bundle!!.getString(packageName), "testpn")
        assertEquals(bundle.getString(wakelockName), "testwn")
        assertEquals(bundle.getBoolean(flaG), true)
    }
}