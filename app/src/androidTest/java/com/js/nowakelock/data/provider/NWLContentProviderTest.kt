package com.js.nowakelock.data.provider

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.TestData
import com.js.nowakelock.data.db.AppDatabase
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class NWLContentProviderTest {

    private var mContentResolver: ContentResolver? = null
    private lateinit var db: AppDatabase

    private val authority = "com.js.nowakelock"

    private val packageName = "PackageName"
    private val wakelockName = "WakelockName"
    private val flaG = "Flag"

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = AppDatabase.getInstance(context)
        mContentResolver = context.contentResolver
    }

    @Test
    fun query() {
//        runBlocking {
//            db.appInfoDao().insertAll(TestData.appInfos)
//            db.wakeLockDao().insertAll(TestData.wakeLocks)
//        }

        val url = Uri.parse("content://$authority/flag")
        val tmp = mContentResolver?.query(url, arrayOf(""), "p1", null, null)


        assertThat<Cursor>(tmp, Matchers.notNullValue())
        assertEquals(tmp?.count, 0)
    }

    @Test
    fun insert() {
        val url = Uri.parse("content://$authority/upCount")
        val newValues = ContentValues().apply {
            /*
             * Sets the values of each column and inserts the word. The arguments to the "put"
             * method are "column name" and "value"
             */
            put(packageName, "example.user")
            put(wakelockName, "en_US")
        }

        val tmp = mContentResolver?.insert(url, newValues)

        LogUtil.d("test1", tmp.toString())

    }
}