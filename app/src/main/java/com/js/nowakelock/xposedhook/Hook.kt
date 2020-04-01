package com.js.nowakelock.xposedhook

import android.os.IBinder
import android.os.WorkSource
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

val TAG = "Xposed.NoWakeLock"
lateinit var mcR: cR

fun hookWakeLocks(lpparam: LoadPackageParam) {
    XposedHelpers.findAndHookMethod("com.android.server.power.PowerManagerService",
        lpparam.classLoader,
        "acquireWakeLockInternal",
        IBinder::class.java,
        Int::class.javaPrimitiveType,
        String::class.java,//wakeLockName
        String::class.java,//packageName
        WorkSource::class.java,
        String::class.java,
        Int::class.javaPrimitiveType,
        Int::class.javaPrimitiveType,
        object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {
                val pN = param.args[3] as String
                val wN = param.args[2] as String
                val lock = param.args[0] as IBinder
                val uId = param.args[6] as Int
                handleWakeLockAcquire(param, pN, wN, lock, uId)
            }
        })

    XposedHelpers.findAndHookMethod("com.android.server.power.PowerManagerService",
        lpparam.classLoader,
        "releaseWakeLockInternal",
        IBinder::class.java,
        Int::class.javaPrimitiveType,
        object : XC_MethodHook() {
            @Throws(Throwable::class)
            override fun beforeHookedMethod(param: MethodHookParam) {
                val lock = param.args[0] as IBinder
                handleWakeLockRelease(param, lock)
            }
        })
}

fun handleWakeLockAcquire(
    param: XC_MethodHook.MethodHookParam,
    pN: String,
    wN: String,
    lock: IBinder,
    uId: Int
) {
    log("$TAG $pN:hookWakeLocks wakelock $wN Acquire")
    //record
//    mcR.upCount(pN, wN)
    //if block
//    if (!mcR.getFlag(pN, wN)) {
//        mcR.upBlockCount(pN, wN)
//        //Deny the wakelock
//        param.result = null
//    }
}

fun handleWakeLockRelease(param: XC_MethodHook.MethodHookParam, lock: IBinder) {
//    log("$TAG $pN: Release")
//    TODO("record lock time")
}
