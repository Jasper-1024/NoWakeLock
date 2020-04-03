package com.js.nowakelock.xposedhook

<<<<<<< HEAD
import android.app.AndroidAppHelper
import android.content.Context
=======
>>>>>>> origin/dev
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.IXposedHookZygoteInit.StartupParam
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam


open class XposedModule : IXposedHookZygoteInit, IXposedHookLoadPackage {
<<<<<<< HEAD
    //Context
    lateinit var context: Context
=======
    private val TAG = "Xposed.NoWakeLock"
>>>>>>> origin/dev

    override fun initZygote(startupParam: StartupParam?) {
        XposedBridge.log("$TAG initZygote")
    }

    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
<<<<<<< HEAD
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
=======
        XposedBridge.log("$TAG ${lpparam.packageName}")
        try {
//            findAndHookMethod(
//                "android.app.Application",
//                lpparam.classLoader,
//                "onCreate",
//                object : XC_MethodHook() {
//                    @Throws(Throwable::class)
//                    override fun afterHookedMethod(param: MethodHookParam) {
//                        XposedBridge.log("$TAG ${lpparam.packageName} afterHookedMethod")
//                        bind(param as Application)
//                        super.afterHookedMethod(param)
//                    }
//                })
////            XposedBridge.hookAllMethods(
////                Application::class.java,
////                "onCreate",
////                object : XC_MethodHook() {
////                    @Throws(Throwable::class)
////                    protected fun after(param: MethodHookParam?) {
////                        XposedBridge.log("$TAG ${lpparam.packageName}  ")
////                    }
////                })
//            XposedBridge.hookAllMethods(Activity::class.java, "onResume", object : XC_MethodHook() {
//                @Throws(Throwable::class)
//                protected fun before(param: MethodHookParam) {
//                    bind((param.thisObject as Activity)!!)
//                    XposedBridge.log("$TAG ${lpparam.packageName} 1")
//                }
//
//                @Throws(Throwable::class)
//                protected fun after(param: MethodHookParam?) {
//                }
//            })
//            XposedBridge.hookAllMethods(Activity::class.java, "onPause", object : XC_MethodHook() {
//                @Throws(Throwable::class)
//                protected fun before(param: MethodHookParam) {
//                    unbind((param.thisObject as Activity)!!)
//                    XposedBridge.log("$TAG ${lpparam.packageName} 2")
//                }
//
//                @Throws(Throwable::class)
//                protected fun after(param: MethodHookParam?) {
//                }
//            })
        } catch (e: Error) {
            XposedBridge.log("$TAG ${lpparam.packageName} $e")
>>>>>>> origin/dev
        }
    }
}