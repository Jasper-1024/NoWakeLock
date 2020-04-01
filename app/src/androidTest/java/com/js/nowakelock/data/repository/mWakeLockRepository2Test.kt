package com.js.nowakelock.data.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.js.nowakelock.LiveDataTestUtil
import com.js.nowakelock.data.TestData
import com.js.nowakelock.data.db.AppDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class mWakeLockRepository2Test {
//
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var wLR: mWakeLockRepository2
//    private lateinit var db: AppDatabase
//
//    @Before
//    fun setUp() {
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        db = Room.inMemoryDatabaseBuilder(
//            context, AppDatabase::class.java
//        ) // allowing main thread queries, just for testing
//            .allowMainThreadQueries()
//            .build()
//        wLR = mWakeLockRepository2(db.wakeLockDao())
//    }
//
//    @After
//    fun tearDown() {
//        db.close()
//    }
//
//    @Test
//    fun upCount() {
//
//        runBlocking {
//            db.appInfoDao().insertAll(TestData.appInfos)
//        }
//
//        runBlocking {
//            wLR.upCount(TestData.pN, "w1")
//            wLR.upCount(TestData.pN, "w1")
//        }
//        CountDownLatch(1).await(2, TimeUnit.SECONDS)
//
////        LogUtil.d("test13",wLR.packageNamesHS.toString())
//        assertEquals(wLR.wakeLocksHM["w1"]?.count, 2)
//    }
//
//    @Test
//    fun upBlockCount() {
//
//        runBlocking {
//            db.appInfoDao().insert(TestData.appInfos[0])
//        }
//
//        runBlocking {
//            wLR.upBlockCount(TestData.pN, "w1")
//            wLR.upBlockCount(TestData.pN, "w1")
//        }
//
//        assertEquals(wLR.wakeLocksHM["w1"]?.count, 2)
//        assertEquals(wLR.wakeLocksHM["w1"]?.blockCount, 2)
//    }
//
//    @Test
//    fun rstCount() {
//
//        runBlocking {
//            db.appInfoDao().insert(TestData.appInfos[0])
//        }
//
//        runBlocking {
//            wLR.upBlockCount(TestData.pN, "w1")
//            wLR.upBlockCount(TestData.pN, "w1")
//            wLR.rstCount(TestData.pN, "w1")
//        }
//        assertEquals(wLR.wakeLocksHM["w1"]?.count, 0)
//        assertEquals(wLR.wakeLocksHM["w1"]?.blockCount, 0)
//    }
//
//    @Test
//    fun insertAll() {
//
//        runBlocking {
//            db.appInfoDao().insert(TestData.appInfos[0])
//        }
//
//        runBlocking {
//            wLR.upBlockCount(TestData.pN, "w1")
//            wLR.upBlockCount(TestData.pN, "w1")
//            wLR.insertAll()
//        }
//        assertEquals(
//            LiveDataTestUtil.getValue(
//                db.wakeLockDao().loadAllWakeLocks(TestData.pN)
//            )[0].count, 2
//        )
//    }
}