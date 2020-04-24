package com.js.nowakelock.data.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.js.nowakelock.LiveDataTestUtil
import com.js.nowakelock.data.TestData
import com.js.nowakelock.data.db.dao.AppInfoDao
import com.js.nowakelock.data.db.entity.AppInfo_st
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppInfoDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var aIDao: AppInfoDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        aIDao = db.appInfoDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

//    @Test
//    fun load_without_Insert() {
//        val appInfos = aIDao.loadAllAppInfos()
//        assertTrue(LiveDataTestUtil.getValue(appInfos).isEmpty())
//    }

//    @Test
//    @Throws(Exception::class)
//    fun loadAll() {
//
//        runBlocking {
//            aIDao.insertAll(TestData.appInfos)
//        }
//
//        assertEquals(runBlocking {
//            LiveDataTestUtil.getValue(aIDao.loadAllAppInfos())
//        }, TestData.appInfos)
//    }

    @Test
    @Throws(Exception::class)
    fun load() {
        runBlocking {
            aIDao.insert(TestData.appInfos[0])
        }

        assertEquals(
            runBlocking { aIDao.loadAppInfo(TestData.appInfos[0].packageName) },
            TestData.appInfos[0]
        )
    }

    @Test
    @Throws(Exception::class)
    fun load_st() {
        runBlocking {
            aIDao.insert(TestData.appInfoST)
        }

        assertEquals(
            LiveDataTestUtil.getValue(aIDao.loadAppSetting(TestData.appInfoST.packageName)),
            TestData.appInfoST
        )
    }
}