package com.js.nowakelock.data.provider

import android.content.Context
import android.os.Bundle
import com.js.nowakelock.base.stringToType
import com.js.nowakelock.data.db.InfoDatabase
import com.js.nowakelock.data.db.entity.Info
import kotlinx.coroutines.runBlocking

enum class ProviderMethod(var value: String) {
    GetAppSt("GetInfo"),
    GetExtends("GetExtends")
}

class XProvider(
    context: Context
) {
    private val tag = "ProviderHandler"

    private var db: InfoDatabase = InfoDatabase.getInstance(context)

    companion object {
        @Volatile
        private var instance: XProvider? = null

        fun getInstance(context: Context): XProvider {
            if (instance == null) {
                instance = XProvider(context)
            }
            return instance!!
        }
    }

    fun getMethod(methodName: String, bundle: Bundle): Bundle? {
        return when (methodName) {
//            ProviderMethod.GetAppSt.value -> getAppSt(bundle)
//            ProviderMethod.GetExtends.value -> getExtends(bundle)
            "test" -> test(bundle)
            else -> null
        }
    }

    private fun test(bundle: Bundle): Bundle {
        val name: String = bundle.getString("name") ?: ""
        val type: String = bundle.getString("type") ?: ""
        val count: Int = bundle.getInt("count")

        val info = Info(
            name = name,
            type = stringToType(type),
            count = count
        )

        runBlocking {
            db.infoDao().insert(info)
            db.infoDao().upBlockCountPO(name, stringToType(type))
        }

        val tmp = Bundle()
        tmp.putString("name", name)
        return tmp
    }
}