package com.js.nowakelock.data.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.js.nowakelock.data.db.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WakeLockRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var wLR: WakeLockRepository
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        wLR = WakeLockRepository(db.wakeLockDao())
    }

    @After
    fun tearDown() {
        db.close()
    }
}