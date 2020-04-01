package com.js.nowakelock.xposedhook

import android.app.AndroidAppHelper
import android.content.Context
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam


open class XposedModule : IXposedHookZygoteInit, IXposedHookLoadPackage {
    //Context
    lateinit var context: Context

    override fun initZygote(startupParam: StartupParam?) {
        XposedBridge.log("$TAG initZygote")
    }

    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
        val pN = lpparam.packageName
        log("$TAG $pN:hook start")
        //get Context
        context = AndroidAppHelper.currentApplication()
        //get cR
        mcR = cR.getInstance(context)

//        hookWakeLocks(lpparam)

        if (mcR.run(pN)) {
            log("$TAG $pN:hookWakeLocks 1")
            try {
                log("$TAG $pN:hookWakeLocks 2")
                hookWakeLocks(lpparam)
            } catch (e: Error) {
                XposedBridge.log("$TAG $pN hookWakeLocks err: $e")
            }
        }
    }
}