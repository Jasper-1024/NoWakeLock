package com.js.nowakelock.xposedhook.model

import android.content.Context
import android.os.Bundle
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.provider.ProviderMethod
import com.js.nowakelock.data.provider.getURI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object XpRecord {
    /**
     * count++
     * @param name String
     * @param packageName String
     * @param type Type
     * @param context Context
     */
    fun upCount(name: String, packageName: String, type: Type, context: Context, userId: Int = 0) =
        CoroutineScope(Dispatchers.Default).launch {
            val args = Bundle()
            args.let {
                it.putString("name", name)
                it.putString("type", type.value)
                it.putString("packageName", packageName)
                it.putInt("userId", userId)
            }
            val method = ProviderMethod.UpCount.value
            getCPResult(context, method, args)
        }

    /**
     * blockCount++
     * @param name String
     * @param packageName String
     * @param type Type
     * @param context Context
     */
    fun upBlockCount(
        name: String, packageName: String, type: Type,
        context: Context, userId: Int = 0
    ) = CoroutineScope(Dispatchers.Default).launch {
        val args = Bundle()
        args.let {
            it.putString("name", name)
            it.putString("type", type.value)
            it.putString("packageName", packageName)
            it.putInt("userId", userId)
        }
        val method = ProviderMethod.UpBlockCount.value

        getCPResult(context, method, args)
    }

    /**
     * countTime + time
     * @param time Long
     * @param name String
     * @param packageName String
     * @param type Type
     * @param context Context
     */
    fun upCountTime(
        time: Long, name: String, packageName: String, type: Type,
        context: Context, userId: Int
    ) = CoroutineScope(Dispatchers.Default).launch {
        val args = Bundle()
        args.let {
            it.putString("name", name)
            it.putString("type", type.value)
            it.putString("packageName", packageName)
            it.putLong("time", time)
            it.putInt("userId", userId)
        }
        val method = ProviderMethod.UpCountTime.value

        getCPResult(context, method, args)
    }

    private fun getCPResult(context: Context, method: String, args: Bundle): Bundle? {
        val contentResolver = context.contentResolver
        return contentResolver.call(getURI(), "NoWakelock", method, args)
    }
}