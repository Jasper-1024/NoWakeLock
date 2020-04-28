package com.js.nowakelock.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.js.nowakelock.data.db.converters.Converters
import com.js.nowakelock.data.db.dao.AppInfoDao
import com.js.nowakelock.data.db.dao.WakeLockDao
import com.js.nowakelock.data.db.entity.*

@Database(
    entities = [AppInfo::class, AppInfo_st::class, WakeLock::class, WakeLock_st::class, Alarm::class, Alarm_st::class],
    version = 3
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
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()


        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `appInfo_st` (`packageName_st` TEXT NOT NULL, `flag` INTEGER NOT NULL, `allowTimeinterval` INTEGER NOT NULL, `rE_Wakelock` TEXT NOT NULL, `rE_Alarm` TEXT NOT NULL, `rE_Service` TEXT NOT NULL, PRIMARY KEY(`packageName_st`))"
                )
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `alarm` (`alarmName` TEXT NOT NULL, `alarm_packageName` TEXT NOT NULL, `alarm_count` INTEGER NOT NULL, `alarm_blockCount` INTEGER NOT NULL, `alarm_countTime` INTEGER NOT NULL, `alarm_blockCountTime` INTEGER NOT NULL, PRIMARY KEY(`alarmName`))"
                )
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `alarm_st` (`alarmName_st` TEXT NOT NULL, `flag` INTEGER NOT NULL, `allowTimeinterval` INTEGER NOT NULL, PRIMARY KEY(`alarmName_st`))"
                )
            }
        }

    }

}