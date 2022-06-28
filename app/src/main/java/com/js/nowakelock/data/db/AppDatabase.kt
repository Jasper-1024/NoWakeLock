package com.js.nowakelock.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.js.nowakelock.data.db.converters.SetConvert
import com.js.nowakelock.data.db.converters.TypeConvert
import com.js.nowakelock.data.db.dao.AppInfoDao
import com.js.nowakelock.data.db.dao.AppStDao
import com.js.nowakelock.data.db.dao.StDao
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.AppSt
import com.js.nowakelock.data.db.entity.St

@Database(
    entities = [
        AppInfo::class, AppSt::class, St::class
    ],
    version = 1
)
@TypeConverters(SetConvert::class, TypeConvert::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appInfoDao(): AppInfoDao
    abstract fun appStDao(): AppStDao
    abstract fun stDao(): StDao

    companion object {
        private const val DATABASE_NAME = "noWakelock_db"

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
        )
//            .addMigrations()
            .build()
    }
}