package com.js.nowakelock.xposedhook

import de.robv.android.xposed.*
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam


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

        if (lpparam.packageName == "android") {
            AlarmHook.hookAlarm(lpparam)
//            ServiceHook.hookService(lpparam)
            WakelockHook.hookWakeLocks(lpparam)
        }
    }
}
