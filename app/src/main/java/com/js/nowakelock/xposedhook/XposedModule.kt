package com.js.nowakelock.xposedhook

import android.os.Process
import com.js.nowakelock.BuildConfig
import com.js.nowakelock.xposedhook.alarm.AlarmHook
import com.js.nowakelock.xposedhook.service.ServiceHook
import com.js.nowakelock.xposedhook.test.WakelockHook2
import com.js.nowakelock.xposedhook.wakelock.WakelockHook
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

        if (lpparam.packageName == "android") {
//            AlarmHook.hookAlarm(lpparam)
//            ServiceHook.hookService(lpparam)
//            WakelockHook.hookWakeLocks(lpparam)
            WakelockHook2.hookWakeLocks(lpparam)
        }
    }
}
