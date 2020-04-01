package com.js.nowakelock.abandoned

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import com.js.nowakelock.service.NWLService
import de.robv.android.xposed.XposedBridge

//var iRemoteService: NWLService? = null
//
//val mConnection = object : ServiceConnection {
//
//    // Called when the connection with the service is established
//    override fun onServiceConnected(className: ComponentName, service: IBinder) {
//        // Following the example above for an AIDL interface,
//        // this gets an instance of the IRemoteInterface, which we can use to call on the service
//        iRemoteService = NWLService.Stub.asInterface(service)
//    }
//
//    // Called when the connection with the service disconnects unexpectedly
//    override fun onServiceDisconnected(className: ComponentName) {
//        XposedBridge.log("Service has unexpectedly disconnected")
//        iRemoteService = null
//    }
//}
