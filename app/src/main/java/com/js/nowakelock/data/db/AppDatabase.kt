package com.js.nowakelock.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.js.nowakelock.data.db.converters.Converters
import com.js.nowakelock.data.db.dao.AppInfoDao
import com.js.nowakelock.data.db.dao.WakeLockDao
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.AppInfo_st
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.data.db.entity.WakeLock_st

@Database(
    entities = [AppInfo::class, AppInfo_st::class, WakeLock::class, WakeLock_st::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appInfoDao(): AppInfoDao
    abstract fun wakeLockDao(): WakeLockDao

    companion object {
        private const val DATABASE_NAME = "nowakelock-db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildInstance(context).also {
                    instance = it
                }
            }

        private fun buildInstance(context: Context) = Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

}