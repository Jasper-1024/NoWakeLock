package com.js.nowakelock.xposedhook

import android.app.AndroidAppHelper
import android.content.Context
import android.os.Process
import com.js.nowakelock.data.db.entity.WakeLock
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam


open class XposedModule : IXposedHookZygoteInit, IXposedHookLoadPackage {
    private val TAG = "Xposed.NoWakeLock"
    private lateinit var pN: String

    companion object;

    override fun initZygote(startupParam: StartupParam?) {
        XposedBridge.log("$TAG initZygote")
    }

    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        pN = lpparam.packageName
        XposedBridge.log("$TAG $pN: handleLoadPackage ,mypid ${Process.myUid()}")

        if (lpparam.packageName == "android") {
//            XposedBridge.log("$TAG $pN:2 handleLoadPackage ,uid ${Process.myUid()}")
//            hookWakeLocks(lpparam, AndroidAppHelper.currentApplication())
            xptest.hookWakeLocks(lpparam)
        }
    }
}
