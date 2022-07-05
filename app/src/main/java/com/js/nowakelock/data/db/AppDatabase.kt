package com.js.nowakelock.data.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.js.nowakelock.data.db.converters.SetConvert
import com.js.nowakelock.data.db.converters.TypeConvert
import com.js.nowakelock.data.db.dao.AppInfoDao
import com.js.nowakelock.data.db.dao.AppStDao
import com.js.nowakelock.data.db.dao.DADao
import com.js.nowakelock.data.db.entity.*

@Database(
    entities = [
        AppInfo::class, AppSt::class, St::class, Info::class
    ],
    views = [AppCount::class],
    version = 2,
    autoMigrations = [
        androidx.room.AutoMigration(from = 1, to = 2)
    ]
)
@TypeConverters(SetConvert::class, TypeConvert::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appInfoDao(): AppInfoDao
    abstract fun appStDao(): AppStDao
    abstract fun dADao(): DADao

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