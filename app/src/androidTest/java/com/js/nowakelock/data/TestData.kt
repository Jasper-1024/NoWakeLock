//package com.js.nowakelock.data
//
//import com.js.nowakelock.data.db.entity.*
//
//class TestData {
//    companion object {
//
//        var alarms = getAlarm()
//
//        var serviceStm = getServices()
//
//        var wakeLocks = getwakeLocks()
//
//        val alarmST = AlarmSt("test", false, 10)
//
//        val serviceSt = ServiceSt("test", false, 10)
//
//        val wakeLockSt = WakeLockSt("test", false, 10)
//
//        var appInfos = getInfos()
//
//        val appInfoST = AppInfoSt("test", false, 10)
//
//        val pN = "p1"
//
//        private fun getAlarm(): MutableList<AlarmInfo> {
//            val tmp: MutableList<AlarmInfo> = mutableListOf()
//
//            for (i in 1..10 step 1) {
//                tmp.add(AlarmInfo("a$i", pN, count = 1))
//            }
//            return tmp
//        }
//
//        private fun getServices(): MutableList<ServiceInfo> {
//            val tmp: MutableList<ServiceInfo> = mutableListOf()
//
//            for (i in 1..10 step 1) {
//                tmp.add(ServiceInfo("s$i", pN, count = 1))
//            }
//            return tmp
//        }
//
//        private fun getwakeLocks(): MutableList<WakeLockInfo> {
//            val tmp: MutableList<WakeLockInfo> = mutableListOf()
//
//            for (i in 1..10 step 1) {
//                tmp.add(WakeLockInfo("w$i", pN, count = 1))
//            }
//            return tmp
//        }
//
//        private fun getInfos(): MutableList<AppInfo> {
//            val appInfos: MutableList<AppInfo> = mutableListOf()
//
//            for (i in 1..10 step 1) {
//                appInfos.add(
//                    AppInfo(
//                        "p$i",
//                        i,
//                        "$i"
//                    )
//                )
//            }
//            return appInfos
//        }
//    }
//}