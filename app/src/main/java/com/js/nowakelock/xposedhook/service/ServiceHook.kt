package com.js.nowakelock.xposedhook.service

import android.app.AndroidAppHelper
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.js.nowakelock.test.Test
import com.js.nowakelock.test.Test2
import com.js.nowakelock.xposedhook.authority
import com.js.nowakelock.xposedhook.log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ServiceHook {
    companion object {
        fun hookService(lpparam: XC_LoadPackage.LoadPackageParam) {
            when (Build.VERSION.SDK_INT) {
                //Try for alarm hooks for API levels >= 29 (Q or higher)
                Build.VERSION_CODES.Q -> serviceHook29(lpparam)
                //Try for alarm hooks for API levels 26 ~ 28 (O ~ P)
                in Build.VERSION_CODES.O..Build.VERSION_CODES.P -> serviceHook26to28(lpparam)
                //Try for alarm hooks for API levels 24 ~ 25 (N)
                in Build.VERSION_CODES.N..Build.VERSION_CODES.N_MR1 -> serviceHook24to25(lpparam)
            }
        }

        private fun serviceHook29(lpparam: XC_LoadPackage.LoadPackageParam) {
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
                Boolean::class.java,//allowBackgroundActivityStarts
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        log("serviceHook29")
                        hookStartServiceLocked(AndroidAppHelper.currentApplication().applicationContext)
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
                        log("serviceHook26to28")
//                        val callingPackage = param.args[6] as String
//                        val context = XposedHelpers.getObjectField(
//                            param.thisObject, "mContext"
//                        ) as Context
//                        hookStartServiceLocked(param, callingPackage, context)
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
                        log("serviceHook24to25")
//                        val callingPackage = param.args[5] as String
//                        val context = XposedHelpers.getObjectField(
//                            param.thisObject, "mContext"
//                        ) as Context
//                        hookStartServiceLocked(param, callingPackage, context)
                    }
                })
        }

        private fun hookStartServiceLocked(
//            param: XC_MethodHook.MethodHookParam,
//            callingPackage: String?,
            context: Context
        ) {
            GlobalScope.launch(Dispatchers.Default) {
                try {
                    log("test1")

                    val method = "test"
                    val url = Uri.parse("content://$authority")

                    val extras = Bundle()
                    extras.putSerializable("Test", Test("test1", "package1", 2, 2, 2, 2))
                    extras.putParcelable("Test", Test2("test1", "package1", 2, 2, 2, 2))

                    val bundle = context.contentResolver.call(url, method, null, extras)

                    log("test2 bundle: $bundle")
                    if (bundle != null) {
//                        bundle.classLoader = context.classLoader
                        val tmp2 = bundle.getSerializable("Test")
                        log("test2 Test:$tmp2")
                    }
                } catch (e: java.lang.Exception) {
                    log("test err: $e")
                }
            }
        }

//        private suspend fun getBundle(method: String, context: Context): Bundle? =
//            withContext(Dispatchers.IO) {
//                val url = Uri.parse("content://$authority")
//                val contentResolver = context.contentResolver
//                val tmp2 = Bundle()
//                val temp = Test("test", "package", 1, 1, 1, 1)
//                tmp2.putParcelable("Test", temp)
//                return@withContext try {
//                    val tmp = contentResolver.call(url, method, null, tmp2)
//                    tmp
//                } catch (e: Exception) {
//                    null
//                }
//            }


    }
}