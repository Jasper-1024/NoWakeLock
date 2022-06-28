package com.js.nowakelock.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.js.nowakelock.BasicApp
import com.js.nowakelock.data.db.Type

class SetConvert {
    @TypeConverter
    fun stringToSet(value: String?): Set<String>? {
        val type = object : TypeToken<Set<String>>() {}.type
        return BasicApp.gson.fromJson(value, type)
    }

    @TypeConverter
    fun setToString(values: Set<String>?): String? {
        return BasicApp.gson.toJson(values)
    }
}

class TypeConvert {
    @TypeConverter
    fun stringToType(value: String): Type {
        return when (value) {
            "Wakelock" -> Type.Wakelock
            "Alarm" -> Type.Alarm
            "Service" -> Type.Service
            else -> Type.UnKnow
        }
    }

    @TypeConverter
    fun typeToString(type: Type): String {
        return type.value
    }
}