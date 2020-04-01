package com.js.nowakelock.abandoned

import android.os.Bundle

interface Handler {
    fun handler(bundle: Bundle?): Bundle
    fun getMethodName(): String
}