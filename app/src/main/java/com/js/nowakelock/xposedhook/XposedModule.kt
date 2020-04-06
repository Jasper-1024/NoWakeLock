package com.js.nowakelock.xposedhook

import android.app.AndroidAppHelper
import android.content.Context
import android.os.Process
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam


open class XposedModule : IXposedHookZygoteInit, IXposedHookLoadPackage {
    private val TAG = "Xposed.NoWakeLock"
    private lateinit var pN: String

    private lateinit var context: Context

//    private val authority = "com.js.nowakelock"
//    private lateinit var mContentResolver: ContentResolver

    override fun initZygote(startupParam: StartupParam?) {
        XposedBridge.log("$TAG initZygote")
    }

    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        pN = lpparam.packageName
//        XposedBridge.log("$TAG $pN: handleLoadPackage ,uid ${Process.myUid()}")

//        findAndHookMethod("android.app.Application", lpparam.classLoader, "attach",
//            Context::class.java, object : XC_MethodHook() {
//                @Throws(Throwable::class)
//                override fun afterHookedMethod(param: MethodHookParam) {
//                    super.afterHookedMethod(param)
//                    XposedBridge.log("$TAG $pN: Application")
//
//                    context = param.args[0] as Context
//                }
//            })

//                    mContentResolver = context!!.contentResolver
//                    val url = Uri.parse("content://$authority/test")
//                    val newValues = ContentValues().apply {
//                        put("PackageName", "example.user")
//                        put("WakelockName", "en_US")
//                    }
//
//                    try {
//                        val tmp = mContentResolver.insert(url, newValues)
//                        XposedBridge.log("$TAG $pN: mContentResolver $tmp")
//                    } catch (e: Exception) {
//                        XposedBridge.log("$TAG $pN: err $e")
//                    }


        hookWakeLocks(lpparam, AndroidAppHelper.currentApplication())

//        mContentResolver = AndroidAppHelper.currentApplication().applicationContext.contentResolver
//        mContentResolver = context?.contentResolver
//
//        val url = Uri.parse("content://$authority/test")
//        val newValues = ContentValues().apply {
//            put("PackageName", "example.user")
//            put("WakelockName", "en_US")
//        }
//
//        try {
//            val tmp = mContentResolver.insert(url, newValues)
//            XposedBridge.log("$TAG $pN: mContentResolver $tmp")
//        } catch (e: Exception) {
//            XposedBridge.log("$TAG $pN: err $e")
//        }

    }
}
