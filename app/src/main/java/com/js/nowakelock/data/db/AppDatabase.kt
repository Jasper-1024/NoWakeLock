package com.js.nowakelock.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.js.nowakelock.data.db.converters.Converters
import com.js.nowakelock.data.db.dao.*
import com.js.nowakelock.data.db.entity.*
import com.js.nowakelock.data.db.network.Description

@Database(
    entities = [AppInfo::class, AppInfoSt::class, WakeLockInfo::class, WakeLockSt::class,
        AlarmInfo::class, AlarmSt::class, ServiceInfo::class, ServiceSt::class, Description::class],
    version = 6
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
    abstract fun appInfoDao(): AppInfoDao
    abstract fun infoDao(): InfoDao
    abstract fun serviceDao(): ServiceDao
    abstract fun wakeLockDao(): WakeLockDao
    abstract fun backupDao(): BackupDao

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
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
            .build()


        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `appInfo_st` (`packageName_st` TEXT NOT NULL, `flag` INTEGER NOT NULL, `allowTimeinterval` INTEGER NOT NULL, `rE_Wakelock` TEXT NOT NULL, `rE_Alarm` TEXT NOT NULL, `rE_Service` TEXT NOT NULL, PRIMARY KEY(`packageName_st`))"
                )
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `alarm` (`alarmName` TEXT NOT NULL, `alarm_packageName` TEXT NOT NULL, `alarm_count` INTEGER NOT NULL, `alarm_blockCount` INTEGER NOT NULL, `alarm_countTime` INTEGER NOT NULL, `alarm_blockCountTime` INTEGER NOT NULL, PRIMARY KEY(`alarmName`))"
                )
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `alarm_st` (`alarmName_st` TEXT NOT NULL, `flag` INTEGER NOT NULL, `allowTimeinterval` INTEGER NOT NULL, PRIMARY KEY(`alarmName_st`))"
                )
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `service` (`serviceName` TEXT NOT NULL, `service_packageName` TEXT NOT NULL, `service_count` INTEGER NOT NULL, `service_blockCount` INTEGER NOT NULL, `service_countTime` INTEGER NOT NULL, `service_blockCountTime` INTEGER NOT NULL, PRIMARY KEY(`serviceName`))"
                )
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `service_st` (`serviceName_st` TEXT NOT NULL, `flag` INTEGER NOT NULL, `allowTimeinterval` INTEGER NOT NULL, PRIMARY KEY(`serviceName_st`))"
                )
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `alarm_st_tmp` (
                        `alarmName_st` TEXT NOT NULL, 
                        `flag` INTEGER NOT NULL, 
                        `allowTimeinterval` INTEGER NOT NULL, 
                        `packageName` TEXT NOT NULL, 
                        PRIMARY KEY(`alarmName_st`)
                    )
                    """.trimMargin()
                )
                database.execSQL(
                    """
                    INSERT INTO alarm_st_tmp (alarmName_st, flag, allowTimeinterval, packageName)
                    SELECT st.alarmName_st, st.flag, st.allowTimeinterval, s.alarm_packageName packageName
                    FROM alarm_st st
                    INNER JOIN alarm s
                    ON st.alarmName_st = s.alarmName
                    """.trimIndent()
                )

                database.execSQL("DROP TABLE alarm_st")
                database.execSQL("ALTER TABLE alarm_st_tmp RENAME TO alarm_st")

                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `service_st_tmp` (
                        `serviceName_st` TEXT NOT NULL, 
                        `flag` INTEGER NOT NULL, 
                        `allowTimeinterval` INTEGER NOT NULL, 
                        `packageName` TEXT NOT NULL, 
                        PRIMARY KEY(`serviceName_st`)
                    )
                    """.trimMargin()
                )
                database.execSQL(
                    """
                    INSERT INTO service_st_tmp (serviceName_st, flag, allowTimeinterval, packageName)
                    SELECT st.serviceName_st, st.flag, st.allowTimeinterval, s.service_packageName packageName
                    FROM service_st st
                    INNER JOIN service s
                    ON st.serviceName_st = s.serviceName
                    """.trimIndent()
                )

                database.execSQL("DROP TABLE service_st")
                database.execSQL("ALTER TABLE service_st_tmp RENAME TO service_st")

                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `wakeLock_st_tmp` (
                        `wakeLockName_st` TEXT NOT NULL,
                        `flag` INTEGER NOT NULL, 
                        `allowTimeinterval` INTEGER NOT NULL, 
                        `packageName` TEXT NOT NULL, 
                        PRIMARY KEY(`wakeLockName_st`)
                    )
                    """.trimMargin()
                )
                database.execSQL(
                    """
                    INSERT INTO wakeLock_st_tmp (wakeLockName_st, flag, allowTimeinterval, packageName)
                    SELECT st.wakeLockName_st, st.flag, st.allowTimeinterval, s.wakeLock_packageName packageName
                    FROM wakeLock_st st
                    INNER JOIN wakeLock s
                    ON st.wakeLockName_st = s.wakeLockName
                    """.trimIndent()
                )

                database.execSQL("DROP TABLE wakeLock_st")
                database.execSQL("ALTER TABLE wakeLock_st_tmp RENAME TO wakeLock_st")
            }
        }

        private val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `description` (`name` TEXT NOT NULL, `language` TEXT NOT NULL, `packageName` TEXT NOT NULL, `re` INTEGER NOT NULL, `info` TEXT NOT NULL, PRIMARY KEY(`name`, `language`))"
                )
            }
        }
    }

}