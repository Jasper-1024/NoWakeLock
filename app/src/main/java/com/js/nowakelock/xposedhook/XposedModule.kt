package com.js.nowakelock.xposedhook

import android.os.Process
import com.js.nowakelock.BuildConfig
import de.robv.android.xposed.*
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import java.io.File


open class XposedModule : IXposedHookZygoteInit, IXposedHookLoadPackage {
    private val TAG = "Xposed.NoWakeLock"
    private lateinit var pN: String

    override fun initZygote(startupParam: StartupParam?) {
        XposedBridge.log("$TAG : initZygote")
    }

    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        pN = lpparam.packageName
//        XposedBridge.log("$TAG $pN: handleLoadPackage ,mypid ${Process.myUid()}")

//        XpUtil.init()

        if (lpparam.packageName == "android") {
//            XposedBridge.log("$TAG $pN:2 handleLoadPackage ,uid ${Process.myUid()}")
//            hookWakeLocks(lpparam, AndroidAppHelper.currentApplication())
            xptest.hookWakeLocks(lpparam)
        }
//        if (lpparam.packageName.equals(BuildConfig.APPLICATION_ID)) {
//
//            // don't use YourActivity.class here
//
//            findAndHookMethod(
//                "${BuildConfig.APPLICATION_ID}.MainActivity", lpparam.classLoader,
//
//                "isModuleActive", XC_MethodReplacement.returnConstant(true)
//            )
//        }
    }
}
