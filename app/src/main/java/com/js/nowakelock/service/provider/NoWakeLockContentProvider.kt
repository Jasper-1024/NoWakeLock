package com.js.nowakelock.service.provider

import android.content.*
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import com.js.nowakelock.service.NWLService

class NoWakeLockContentProvider : ContentProvider() {

    @Volatile
    private var mService: NWLService? = null
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as NWLService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(): Boolean {
        /*bind server*/
        bind()
        return true
    }

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        //no need handler
        if (extras == null) {
            return null
        }
        /*if mService is null*/
        if (!mBound) {
            bind()
        }

        return mService?.serviceModel?.let {
            ProviderHandler.getInstance(it).getMethod(method, extras)
        }

//        val bundle = Bundle()
//        bundle.putBoolean(method, true)
//        return super.call(method, arg, extras)
//        LogUtil.d("test1", method)
//        LogUtil.d("test1", mService?.serviceModel!!.TAG)
//        return bundle
    }


    private fun bind() {
        Intent(this.context, NWLService::class.java).also { intent ->
            context?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
