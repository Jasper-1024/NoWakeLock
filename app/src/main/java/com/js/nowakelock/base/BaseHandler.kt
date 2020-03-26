package com.js.nowakelock.base

open class BaseHandler {
    private val TAG = "BaseHandler"
    open fun Click() {
        LogUtil.d(TAG, "click")
    }
}