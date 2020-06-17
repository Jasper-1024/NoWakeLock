package com.js.nowakelock.data.db

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private val TEST_DB = "migration-test"

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

    val MIGRATION_3_4 = object : Migration(3, 4) {
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


    private val ALL_MIGRATIONS = arrayOf(
        MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5
    )


    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        var db = helper.createDatabase(TEST_DB, 1).apply {
            // db has schema version 1. insert some data using SQL queries.
            // You cannot use DAO classes because they expect the latest schema.
//            execSQL(...)

            // Prepare for the next version.
            close()
        }

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
    }


    @Test
    @Throws(IOException::class)
    fun migrateAll() {
        // Create earliest version of the database.
        helper.createDatabase(TEST_DB, 1).apply {
            close()
        }

        // Open latest version of the database. Room will validate the schema
        // once all migrations execute.
        Room.databaseBuilder<AppDatabase>(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java, TEST_DB
        ).addMigrations(*ALL_MIGRATIONS).build().apply {
            openHelper.writableDatabase
            close()
        }
    }

}