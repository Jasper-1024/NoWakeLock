package com.js.nowakelock.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.js.nowakelock.data.dataBase.converters.SetConvert
import com.js.nowakelock.data.dataBase.converters.TypeConvert
import com.js.nowakelock.data.dataBase.dao.InfoDao
import com.js.nowakelock.data.dataBase.entity.Info
import com.js.nowakelock.data.db.converters.Converters

@Database(
    entities = [
        Info::class
    ],
    version = 1
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
//            .addMigrations()
            .build()
    }
}