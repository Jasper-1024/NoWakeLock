package com.js.nowakelock.ui.base

//enum class Type(var value: String) {
//    Wakelock("Wakelock"), Alarm("Alarm"), Service("Service"), Sync("Sync"), Broadcast("Broadcast")
//}

enum class AppType(var type: Int) {
    All(0), User(1), System(2)
}

enum class Sort(var type: Int) {
    Name(0), Count(1), CountTime(3)
}