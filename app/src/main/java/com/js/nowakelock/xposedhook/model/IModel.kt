package com.js.nowakelock.xposedhook.model

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import com.js.nowakelock.xposedhook.XpUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IModel(var type: String) : Model {

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
            dbTime = now
        }
        if (now - stTime > st.updateST) {//update st
            updateST(context)
            stTime = now
        }
    }

    override fun flag(name: String): Boolean {
        return st.flagHM[name] ?: true
    }

    override fun aTi(name: String, lastAllowTime: Long): Boolean {
        return (SystemClock.elapsedRealtime() - lastAllowTime) >= st.atIHM[name] ?: 0
    }

    override fun re(name: String, packageName: String): Boolean {
        val rE: Set<String> = st.rEHM[packageName] ?: mutableSetOf()
//        XpUtil.log("type:$type pN:$packageName $name re:$rE")
        if (rE.isEmpty()) {
            return true
        } else {
            rE.forEach {
                if (name.matches(Regex(it))) {
                    return false
                }
            }

            return true
        }
    }

    override fun upCount(name: String, packageName: String) {
        val tmp: DB = db.dbHM[name] ?: DB(name, packageName)
        tmp.count++
        db.dbHM[name] = tmp
    }

    override fun upBlockCount(name: String, packageName: String) {
        val tmp: DB = db.dbHM[name] ?: DB(name, packageName)
        tmp.count++
        tmp.blockCount++
        db.dbHM[name] = tmp
    }

    override fun upCountTime(name: String, packageName: String, time: Long) {
        val tmp: DB = db.dbHM[name] ?: DB(name, packageName)
        tmp.countTime += time
        db.dbHM[name] = tmp
    }

    override fun upBlockCountTime(name: String, packageName: String, time: Long) {
        val tmp: DB = db.dbHM[name] ?: DB(name, packageName)
        tmp.countTime += time
        tmp.blockCountTime += time
        db.dbHM[name] = tmp
    }

    private fun updateDB(context: Context) {
        if (db.dbHM.size == 0) return
//        XpUtil.log("handleTimer db")
        GlobalScope.launch {
            try {
                val tmp = Bundle()
                tmp.putString(XPM.type, type)
                synchronized(this) {
                    tmp.putSerializable(XPM.db, db)
                }
                call(XPM.dbMethod, tmp, context)//update db
//                XpUtil.log("handleTimer db $db")
                synchronized(this) {
                    db.dbHM.clear()
                }
            } catch (e: Exception) {
                XpUtil.log("handleTimer db err: $e")
            }
        }
    }

    private fun updateST(context: Context) {
//        XpUtil.log("handleTimer st")
        GlobalScope.launch {
            try {
                val tmp = Bundle()
                tmp.putString(XPM.type, type)
                val bundle = call(XPM.stMethod, tmp, context)//get st
                bundle?.let { bun ->//if bundle not null
//                    XpUtil.log("handleTimer st bundle: $bun")
                    bun.getSerializable(XPM.st)?.let {//get st
                        synchronized(this) {
                            st = it as STModel
                        }
                    }
                }
//                XpUtil.log("type:$type handleTimer st: $st")
            } catch (e: Exception) {
                XpUtil.log("handleTimer st err: $e")
            }
        }
    }

    private fun call(method: String, bundle: Bundle, context: Context): Bundle? {
        val url = Uri.parse("content://${XpUtil.authority}")
        val contentResolver = context.contentResolver
        return try {
            contentResolver.call(url, method, null, bundle)
        } catch (e: Exception) {
            XpUtil.log(": record $method err: $e")
            null
        }
    }
}