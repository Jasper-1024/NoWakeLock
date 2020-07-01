package com.js.nowakelock.data.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.js.nowakelock.data.TestData
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.dao.AppInfoDao
import com.js.nowakelock.data.repository.appinforepository.IAppInfoRepository
import com.js.nowakelock.tool.LiveDataTestUtil
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class mAppInfoFRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var aR: IAppInfoRepository
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        aR =
            IAppInfoRepository(
                db.appInfoDao()
            )
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getAppLists() {
        val appInfos = aR.getAppLists()
        assertTrue(LiveDataTestUtil.getValue(appInfos).isEmpty())
        runBlocking { db.appInfoDao().insert(TestData.appInfos) }

        assertEquals(LiveDataTestUtil.getValue(appInfos).size, 10)
    }

    @Test
    fun sync() {
        val appInfos = aR.getAppLists()
        assertTrue(LiveDataTestUtil.getValue(appInfos).isEmpty())
        runBlocking { aR.syncAppInfos() }
        assertTrue(LiveDataTestUtil.getValue(appInfos).isNotEmpty())
    }

    @Test
    fun saveAppSetting() {
//        val appInfoSTs = aR.getAppSetting(TestData.appInfoST.packageName)
//        assertTrue(LiveDataTestUtil.getValue(appInfoSTs) == null)
//        runBlocking { aR.saveAppSetting(TestData.appInfoST) }
//        assertEquals(
//            LiveDataTestUtil.getValue(appInfoSTs),
//            TestData.appInfoST
//        )
//        assertTrue(LiveDataTestUtil.getValue(appInfoSTs).isNotEmpty())
    }

    companion object {
        @Volatile
        private var instance: IAppInfoRepository? = null
        fun getInstance(appInfoDao: AppInfoDao) =
            instance ?: synchronized(this) {
                instance ?: IAppInfoRepository(
                    appInfoDao
                ).also { instance = it }
            }
    }
}