package com.js.nowakelock.xposedhook.model

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import com.js.nowakelock.xposedhook.XpUtil
import com.js.nowakelock.xposedhook.authority
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class mModel(var type: String) : Model {

    @Volatile
    private var st: STModel = STModel() // settings

    @Volatile
    private var db: DBModel = DBModel() // dbs

    private var stTime: Long = 0 //last update ST time
    private var dbTime: Long = 0 //last update DB time

    override fun handleTimer(context: Context) {
        val now = SystemClock.elapsedRealtime()

        if (st.recordFlag && (now - dbTime > st.updateDb || db.dbHM.size > 50)) {//update db
            updateDB(context)
        }
        if (now - stTime > st.updateST) {//update st
            updateST(context)
        }
    }

    override fun flag(name: String): Boolean {
        return st.flagHM[name] ?: true
    }

    override fun aTi(name: String, lastAllowTime: Long): Boolean {
        return (SystemClock.elapsedRealtime() - lastAllowTime) >= st.atIHM[name] ?: 0
    }

    @Synchronized
    override fun re(name: String, packageName: String): Boolean {
        val rE: Set<String> = st.rEHM[name] ?: mutableSetOf()
        if (rE.isEmpty()) {
            return true
        } else {
            synchronized(this) {
                rE.forEach {
                    if (name.matches(Regex(it))) {
                        return false
                    }
                }
            }
            return true
        }
    }

    override fun upCount(name: String) {
        val tmp: DB = db.dbHM[name] ?: DB(name)
        tmp.count++
        db.dbHM[name] = tmp
    }

    override fun upBlockCount(name: String) {
        val tmp: DB = db.dbHM[name] ?: DB(name)
        tmp.count++
        tmp.blockCount++
        db.dbHM[name] = tmp
    }

    private fun updateDB(context: Context) {
        GlobalScope.launch {
            try {
                val tmp = Bundle()
                synchronized(this) {
                    tmp.putSerializable(XPM.db, db)
                    tmp.putString(XPM.type, type)
                }
                call(XPM.dbMethod, tmp, context)//update db
                synchronized(this) {
                    db.dbHM.clear()
                }
            } catch (e: Exception) {
                XpUtil.log("handleTimer db err: $e")
            }
        }
    }

    private fun updateST(context: Context) {
        GlobalScope.launch {
            try {
                val tmp = Bundle()
                tmp.putString(XPM.type, type)
                val bundle = call(XPM.stMethod, tmp, context)//get st
                bundle?.let { bun ->//if bundle not null
                    bun.getSerializable(XPM.st)?.let {//get st
                        synchronized(this) {
                            st = it as STModel
                        }
                    }
                }
            } catch (e: Exception) {
                XpUtil.log("handleTimer st err: $e")
            }
        }
    }

    suspend fun call(method: String, bundle: Bundle, context: Context): Bundle? {
        val url = Uri.parse("content://$authority")
        val contentResolver = context.contentResolver
        return try {
            contentResolver.call(url, method, null, bundle)
        } catch (e: Exception) {
            XpUtil.log(": record $method err: $e")
            null
        }
    }
}