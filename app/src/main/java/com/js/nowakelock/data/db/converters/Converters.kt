package com.js.nowakelock.data.db.converters

import androidx.room.TypeConverter
import com.js.nowakelock.base.Util

/**for Regular expression*/
class Converters {
    @TypeConverter
    fun stringToSet(value: String?): Set<String>? {
        return Util.stringToSet(value)
    }

    @TypeConverter
    fun setToString(values: Set<String>?): String? {
        return Util.setToString(values)
    }
}