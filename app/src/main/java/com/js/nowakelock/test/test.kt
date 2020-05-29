package com.js.nowakelock.test

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

//@Parcelize
//@Serializable
@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
data class Test(
    val name: String?,
    var packageName: String? = "",
    var count: Int = 0,
    var blockCount: Int = 0,
    var countTime: Long = 0,
    var blockCountTime: Long = 0
) : Serializable

@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
@Parcelize
data class Test2(
    val name: String?,
    var packageName: String? = "",
    var count: Int = 0,
    var blockCount: Int = 0,
    var countTime: Long = 0,
    var blockCountTime: Long = 0
) : Parcelable

//fun main() {
//    val bundle = Bundle()
//    bundle.putParcelable("test", Person("test", 1))
//}