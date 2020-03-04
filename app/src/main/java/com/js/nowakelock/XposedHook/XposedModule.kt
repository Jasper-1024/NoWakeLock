package com.js.nowakelock.XposedHook

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

class XposedModule : IXposedHookZygoteInit, IXposedHookLoadPackage {
    private val TAG = "Xposed.NoWakeLock "

    override fun initZygote(startupParam: StartupParam?) {
        XposedBridge.log(TAG + "initZygote")
    }

    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        XposedBridge.log(TAG + lpparam.packageName)
    }
}