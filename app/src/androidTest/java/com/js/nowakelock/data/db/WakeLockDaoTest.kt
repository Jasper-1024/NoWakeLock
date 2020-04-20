package com.js.nowakelock.data.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.js.nowakelock.LiveDataTestUtil
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.TestData
import com.js.nowakelock.data.db.dao.WakeLockDao
import kotlinx.coroutines.runBlocking
import org.junit.*

class WakeLockDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var wLDao: WakeLockDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        wLDao = db.wakeLockDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

//    @Test
//    fun load_without_Insert() {
//        val wakeLocks = wLDao.loadAllWakeLocks(TestData.pN)
//        Assert.assertTrue(LiveDataTestUtil.getValue(wakeLocks).isEmpty())
//    }

    @Test
    fun upAppInfodateCount() {
        runBlocking {
            db.appInfoDao().insertAll(TestData.appInfos)
            db.wakeLockDao().insertAll(TestData.wakeLocks)
            wLDao.updateAppInfoCount(TestData.pN)
        }
        val a = runBlocking { db.appInfoDao().loadAppInfo(TestData.pN).count }
        LogUtil.d("test1", a.toString())
        Assert.assertEquals(a, 10)
    }
}