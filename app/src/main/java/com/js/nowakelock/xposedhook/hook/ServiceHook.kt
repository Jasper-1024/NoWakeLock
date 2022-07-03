package com.js.nowakelock.xposedhook.hook

import android.app.AndroidAppHelper
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.xposedhook.XpUtil
import com.js.nowakelock.xposedhook.model.IModel
import com.js.nowakelock.xposedhook.model.Model
import com.js.nowakelock.xposedhook.model.XPM
import com.js.nowakelock.xposedhook.model.XpRecord
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class ServiceHook {
    companion object {

        @Volatile
        private var lastAllowTime = HashMap<String, Long>()//wakelock last allow time

        fun hookService(lpparam: XC_LoadPackage.LoadPackageParam) {
            when (Build.VERSION.SDK_INT) {
                in Build.VERSION_CODES.S..Build.VERSION_CODES.S_V2 -> serviceHook31to32(lpparam)
                //Try for alarm hooks for API levels = 30 (R)
                Build.VERSION_CODES.R -> serviceHook30(lpparam)
                //Try for alarm hooks for API levels = 29 (Q)
                Build.VERSION_CODES.Q -> serviceHook29(lpparam)
                //Try for alarm hooks for API levels 26 ~ 28 (O ~ P)
                in Build.VERSION_CODES.O..Build.VERSION_CODES.P -> serviceHook26to28(lpparam)
                //Try for alarm hooks for API levels 24 ~ 25 (N)
                in Build.VERSION_CODES.N..Build.VERSION_CODES.N_MR1 -> serviceHook24to25(lpparam)
            }
        }

        private fun serviceHook31to32(lpparam: XC_LoadPackage.LoadPackageParam) {
            XposedHelpers.findAndHookMethod("com.android.server.am.ActiveServices",
                lpparam.classLoader,
                "startServiceLocked",
                "android.app.IApplicationThread",
                Intent::class.java,//service
                String::class.java,//resolvedType
                Int::class.javaPrimitiveType,//callingPid
                Int::class.javaPrimitiveType,//callingUid
                Boolean::class.java,//fgRequired
                String::class.java,//callingPackage
                String::class.java,//callingFeatureId
                Int::class.javaPrimitiveType,//userId
                Boolean::class.java,//allowBackgroundActivityStarts
                IBinder::class.java,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
//                        XpUtil.log("serviceHook29")
                        val service = param.args[1] as Intent?
                        val callingPackage = param.args[6] as String
                        val context: Context =
                            AndroidAppHelper.currentApplication().applicationContext
                        hookStartServiceLocked(
                            param,
                            service,
                            callingPackage,
                            context
                        )
                    }
                })
        }

        private fun serviceHook30(lpparam: XC_LoadPackage.LoadPackageParam) {
            XposedHelpers.findAndHookMethod("com.android.server.am.ActiveServices",
                lpparam.classLoader,
                "startServiceLocked",
                "android.app.IApplicationThread",
                Intent::class.java,//service
                String::class.java,//resolvedType
                Int::class.javaPrimitiveType,//callingPid
                Int::class.javaPrimitiveType,//callingUid
                Boolean::class.java,//fgRequired
                String::class.java,//callingPackage
                String::class.java,//callingFeatureId
                Int::class.javaPrimitiveType,//userId
                Boolean::class.java,//allowBackgroundActivityStarts
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
//                        XpUtil.log("serviceHook29")
                        val service = param.args[1] as Intent?
                        val callingPackage = param.args[6] as String
                        val context: Context =
                            AndroidAppHelper.currentApplication().applicationContext
                        hookStartServiceLocked(
                            param,
                            service,
                            callingPackage,
                            context
                        )
                    }
                })
        }

        private fun serviceHook29(lpparam: XC_LoadPackage.LoadPackageParam) {
            XposedHelpers.findAndHookMethod(
                "com.android.server.am.ActiveServices",
                lpparam.classLoader,
                "startServiceLocked",
                "android.app.IApplicationThread",
                Intent::class.java,//service
                String::class.java,//resolvedType
                Int::class.javaPrimitiveType,//callingPid
                Int::class.javaPrimitiveType,//callingUid
                Boolean::class.java,//fgRequired
                String::class.java,//callingPackage
                Int::class.javaPrimitiveType,//userId
                Boolean::class.java,//allowBackgroundActivityStarts
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
//                        XpUtil.log("serviceHook29")
                        val service = param.args[1] as Intent?
                        val callingPackage = param.args[6] as String
                        val context: Context =
                            AndroidAppHelper.currentApplication().applicationContext
                        hookStartServiceLocked(
                            param,
                            service,
                            callingPackage,
                            context
                        )
                    }
                })
        }

        private fun serviceHook26to28(lpparam: XC_LoadPackage.LoadPackageParam) {
            XposedHelpers.findAndHookMethod("com.android.server.am.ActiveServices",
                lpparam.classLoader,
                "startServiceLocked",
                "android.app.IApplicationThread",
                Intent::class.java,//service
                String::class.java,//resolvedType
                Int::class.javaPrimitiveType,//callingPid
                Int::class.javaPrimitiveType,//callingUid
                Boolean::class.java,//fgRequired
                String::class.java,//callingPackage
                Int::class.javaPrimitiveType,//userId
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
//                        XpUtil.log("serviceHook26to28")
                        val service = param.args[1] as Intent?
                        val callingPackage = param.args[6] as String
                        val context: Context =
                            AndroidAppHelper.currentApplication().applicationContext
                        hookStartServiceLocked(
                            param,
                            service,
                            callingPackage,
                            context
                        )
                    }
                })
        }

        private fun serviceHook24to25(lpparam: XC_LoadPackage.LoadPackageParam) {
            XposedHelpers.findAndHookMethod("com.android.server.am.ActiveServices",
                lpparam.classLoader,
                "startServiceLocked",
                "android.app.IApplicationThread",
                Intent::class.java,//service
                String::class.java,//resolvedType
                Int::class.javaPrimitiveType,//callingPid
                Int::class.javaPrimitiveType,//callingUid
                String::class.java,//callingPackage
                Int::class.javaPrimitiveType,//userId
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
//                        XpUtil.log("serviceHook24to25")
                        val service = param.args[1] as Intent?
                        val callingPackage = param.args[5] as String
                        val context: Context =
                            AndroidAppHelper.currentApplication().applicationContext
                        hookStartServiceLocked(
                            param,
                            service,
                            callingPackage,
                            context
                        )
                    }
                })
        }

        private fun hookStartServiceLocked(
            param: XC_MethodHook.MethodHookParam,
            service: Intent?,
            packageName: String?,
            context: Context
        ) {
            if (service == null || packageName == null) return
            val now = SystemClock.elapsedRealtime()
            val serviceName: String = service.component?.flattenToShortString() ?: ""
//            val serviceName: String = tmp.replace(Regex(".*/"), "")


            val block = false

            if (block) {
                XpUtil.log("$packageName service: $serviceName block")
                param.result = null

                XpRecord.upBlockCount(
                    serviceName, packageName, Type.Service, context
                )//update BlockCount
            } else {
                lastAllowTime[serviceName] = now
                XpRecord.upCount(serviceName, packageName, Type.Service, context)//update Count
            }

        }

        // get service should block or not
//        private fun block(sN: String, packageName: String, aTI: Long): Boolean {
//            return model.flag(sN) && model.re(sN, packageName) && model.aTi(sN, aTI)
//        }

    }
}