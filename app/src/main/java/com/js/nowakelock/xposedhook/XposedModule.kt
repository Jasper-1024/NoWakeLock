package com.js.nowakelock.xposedhook

import com.js.nowakelock.BuildConfig
import com.js.nowakelock.xposedhook.hook.AlarmHook
import com.js.nowakelock.xposedhook.hook.ServiceHook
import com.js.nowakelock.xposedhook.hook.SettingsProviderHook
import com.js.nowakelock.xposedhook.hook.WakelockHook
import de.robv.android.xposed.*
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam


open class XposedModule : IXposedHookZygoteInit, IXposedHookLoadPackage {

    override fun initZygote(startupParam: StartupParam?) {
        XpUtil.log(": initZygote")
    }

    override fun handleLoadPackage(lpparam: LoadPackageParam) {
//        val pN = lpparam.packageName
//        XposedBridge.log("$TAG $pN: handleLoadPackage ,mypid ${Process.myUid()}")

        when (lpparam.packageName) {
            "android" -> {//hook Android system
                try {
                    WakelockHook.hookWakeLocks(lpparam)
                } catch (e: Exception) {
                    XpUtil.log("${e.message}")
                    XpUtil.log("${e.stackTrace}")
                }
                try {
                    AlarmHook.hookAlarm(lpparam)
                } catch (e: Exception) {
                    XpUtil.log("${e.message}")
                    XpUtil.log("${e.stackTrace}")
                }
                try {
                    ServiceHook.hookService(lpparam)
                } catch (e: Exception) {
                    XpUtil.log("${e.message}")
                    XpUtil.log("${e.stackTrace}")
                }
            }
            "com.android.providers.settings" -> {//hook SettingsProvider
                SettingsProviderHook.hook(lpparam)
            }
            BuildConfig.APPLICATION_ID -> {// hook myself
                XposedHelpers.findAndHookMethod(
                    "${BuildConfig.APPLICATION_ID}.ui.mainActivity.MainActivity",
                    lpparam.classLoader,
                    "isModuleActive",
                    XC_MethodReplacement.returnConstant(true)
                )
            }
        }
    }
}
