package com.js.nowakelock.data.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.js.nowakelock.data.db.converters.SetConvert
import com.js.nowakelock.data.db.converters.TypeConvert
import com.js.nowakelock.data.db.dao.InfoDao
import com.js.nowakelock.data.db.entity.Info

@Database(
    entities = [
        Info::class
    ],
    version = 7,
    autoMigrations = [
        androidx.room.AutoMigration(from = 1, to = 2),
        androidx.room.AutoMigration(from = 2, to = 3),
        androidx.room.AutoMigration(from = 3, to = 4),
        androidx.room.AutoMigration(from = 4, to = 5, spec = InfoDatabase.C4To5::class)
    ]
)
@TypeConverters(SetConvert::class, TypeConvert::class)
abstract class InfoDatabase : RoomDatabase() {
    abstract fun infoDao(): InfoDao

    companion object {
        private const val DATABASE_NAME = "info_db"

        @Volatile
        private var instance: InfoDatabase? = null

        fun getInstance(context: Context): InfoDatabase =
            instance ?: synchronized(this) {
                instance ?: buildInstance(context).also {
                    instance = it
                }
            }

        private fun buildInstance(context: Context) = Room.databaseBuilder(
            context, InfoDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration() //if version change, it will delete all data.
            .build()
    }

    @RenameColumn(
        tableName = "info", fromColumnName = "userId", toColumnName = "userid_info"
    )
    class C4To5 : AutoMigrationSpec
}