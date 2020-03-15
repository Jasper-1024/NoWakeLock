package com.js.nowakelock

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.js.nowakelock.db.AppDatabase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DataRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dR: DataRepository
    private lateinit var db: AppDatabase
    private val TAG = "test_dR"
    private val pN = "com.js.nowakelock"
    private var wN = "w1"
    private var wN2 = "w2"

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        dR = DataRepository(db)
        runBlocking {
            dR.getAppInfoHMs()
            dR.getWakeLockHMs()
        }
        runBlocking {
            dR.upBlockCount(pN, wN)
            dR.upBlockCount(pN, wN)
            dR.upBlockCount(pN, wN2)
        }
    }

    @After
    fun tearDown() {
        db.close()
    }

    /**new app install*/
    @Test
    fun upCount() {
//        runBlocking {
//            dR.upCount(pN, wN)
//            dR.upCount(pN, wN)
//            dR.upCount(pN, wN2)
//        }
        //LogUtil.d(TAG,dR.getCount(pN).toString())
        assertEquals(dR.getCount(pN), 3)
        assertEquals(dR.getCount(pN, wN), 2)
        assertEquals(dR.getCount(pN, wN2), 1)
    }

    /**new app install*/
    @Test
    fun upBlockCount() {
        //LogUtil.d(TAG,dR.getCount(pN).toString())
        assertEquals(dR.getCount(pN), 3)
        assertEquals(dR.getCount(pN, wN), 2)
        assertEquals(dR.getCount(pN, wN2), 1)
        assertEquals(dR.getBlockCount(pN), 3)
        assertEquals(dR.getBlockCount(pN, wN), 2)
        assertEquals(dR.getBlockCount(pN, wN2), 1)
    }

    @Test
    fun updateDB() {
        runBlocking {
            dR.saveAllToDb()
        }
        assertEquals(runBlocking { db.appInfoDao().loadAppInfo(pN).count }, 3)
        assertEquals(runBlocking { db.wakeLockDao().loadWakeLock(wN).blockCount }, 2)
        assertEquals(runBlocking { db.wakeLockDao().loadWakeLock(wN2).blockCount }, 1)
    }

//    @Test
//    fun test() {
//        val tmp = runBlocking { dR.getAppInfos() }
//        LogUtil.d(TAG,tmp.toString())
//    }
}