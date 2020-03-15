package com.js.nowakelock.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.js.nowakelock.db.dao.AppInfoDao
import com.js.nowakelock.db.dao.WakeLockDao
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appInfoDao: AppInfoDao
    private lateinit var wakeLockDao: WakeLockDao
    private lateinit var db: AppDatabase


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        appInfoDao = db.appInfoDao()
        wakeLockDao = db.wakeLockDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun loadWithoutInserted() {
        val appinfos = runBlocking {
            appInfoDao.loadAllAppInfos()
        }
        val wakeLocks = runBlocking {
            wakeLockDao.loadAllWakeLocks(TestData.packageName)
        }

        assertTrue(appinfos.isEmpty())
        assertTrue(wakeLocks.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun loadAll() {

        runBlocking {
            appInfoDao.insertAll(TestData.appInfos)
            wakeLockDao.insertAll(TestData.wakeLocks)
        }

        assertEquals(runBlocking {
            appInfoDao.loadAllAppInfos()
        }, TestData.appInfos)

        assertEquals(runBlocking {
            wakeLockDao.loadAllWakeLocks(TestData.packageName)
        }, TestData.wakeLocks)
    }

    @Test
    @Throws(Exception::class)
    fun load() {
        runBlocking {
            appInfoDao.insert(TestData.appInfos[0])
            wakeLockDao.insert(TestData.wakeLocks[0])
        }

        assertEquals(
            runBlocking { appInfoDao.loadAppInfo(TestData.appInfos[0].packageName) },
            TestData.appInfos[0]
        )
        assertEquals(
            runBlocking { wakeLockDao.loadWakeLock(TestData.wakeLocks[0].wakeLockName) },
            TestData.wakeLocks[0]
        )
    }

    @Test
    @Throws(Exception::class)
    fun count() {
        runBlocking { appInfoDao.insert(TestData.appInfos[0]) }
        assertEquals(
            runBlocking { appInfoDao.loadAppInfo(TestData.appInfos[0].packageName).count },
            0
        )

        assertEquals(
            runBlocking {
                appInfoDao.upCount(TestData.appInfos[0].packageName)
                appInfoDao.loadAppInfo(TestData.appInfos[0].packageName).count
            },
            1
        )

        assertEquals(
            runBlocking {
                appInfoDao.rstCount(TestData.appInfos[0].packageName)
                appInfoDao.loadAppInfo(TestData.appInfos[0].packageName).count
            },
            0
        )
    }

    @Test
    @Throws(Exception::class)
    fun blockCount() {
        runBlocking { appInfoDao.insert(TestData.appInfos[0]) }
        assertEquals(
            runBlocking { appInfoDao.loadAppInfo(TestData.appInfos[0].packageName).blockCount },
            0
        )

        assertEquals(
            runBlocking {
                appInfoDao.upBlockCount(TestData.appInfos[0].packageName)
                appInfoDao.loadAppInfo(TestData.appInfos[0].packageName).blockCount
            },
            1
        )

        assertEquals(
            runBlocking {
                appInfoDao.rstBlockCount(TestData.appInfos[0].packageName)
                appInfoDao.loadAppInfo(TestData.appInfos[0].packageName).blockCount
            },
            0
        )
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() {
        runBlocking {
            appInfoDao.insertAll(TestData.appInfos)
            wakeLockDao.insertAll(TestData.wakeLocks)
        }

        runBlocking {
            appInfoDao.deleteAll(TestData.appInfos)
            //wakeLockDao.deleteAll(TestData.wakeLocks)
        }
        val appinfos = runBlocking {
            appInfoDao.loadAllAppInfos()
        }
        val wakeLocks = runBlocking {
            wakeLockDao.loadAllWakeLocks(TestData.packageName)
        }

        assertTrue(appinfos.isEmpty())
        assertTrue(wakeLocks.isEmpty())
    }


}