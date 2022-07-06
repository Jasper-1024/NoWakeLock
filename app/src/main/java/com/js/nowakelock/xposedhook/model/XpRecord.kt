package com.js.nowakelock.xposedhook.model

import android.content.Context
import android.os.Bundle
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.provider.ProviderMethod
import com.js.nowakelock.data.provider.getURI

object XpRecord {
    /**
     * count++
     * @param name String
     * @param packageName String
     * @param type Type
     * @param context Context
     */
    fun upCount(name: String, packageName: String, type: Type, context: Context) {
        val args = Bundle()
        args.let {
            it.putString("name", name)
            it.putString("type", type.value)
            it.putString("packageName", packageName)
        }
        val method = ProviderMethod.UpCount.value

        val result: Bundle? = getCPResult(context, method, args)
//        checkResult(result, method, name, packageName, type)
    }

    /**
     * blockCount++
     * @param name String
     * @param packageName String
     * @param type Type
     * @param context Context
     */
    fun upBlockCount(name: String, packageName: String, type: Type, context: Context) {
        val args = Bundle()
        args.let {
            it.putString("name", name)
            it.putString("type", type.value)
            it.putString("packageName", packageName)
        }
        val method = ProviderMethod.UpBlockCount.value

        val result: Bundle? = getCPResult(context, method, args)
//        checkResult(result, method, name, packageName, type)
    }

    /**
     * countTime + time
     * @param time Long
     * @param name String
     * @param packageName String
     * @param type Type
     * @param context Context
     */
    fun upCountTime(time: Long, name: String, packageName: String, type: Type, context: Context) {
        val args = Bundle()
        args.let {
            it.putString("name", name)
            it.putString("type", type.value)
            it.putString("packageName", packageName)
            it.putLong("time", time)
        }
        val method = ProviderMethod.UpCountTime.value

        val result: Bundle? = getCPResult(context, method, args)
//        checkResult(result, method, name, packageName, type)
    }

//    private fun checkResult(
//        result: Bundle?,
//        method: String,
//        name: String,
//        packageName: String,
//        type: Type
//    ) {
//        result?.let {
//            if (name == (it.getString("name") ?: "")) {
////                XpUtil.log("$packageName ${type.value}:$name $method success")
//                return
//            }
//        }
//        XpUtil.log("$packageName ${type.value}:$name $method failed")
//    }

    private fun getCPResult(context: Context, method: String, args: Bundle): Bundle? {
        val contentResolver = context.contentResolver
        return contentResolver.call(getURI(), "NoWakelock", method, args)
    }
}