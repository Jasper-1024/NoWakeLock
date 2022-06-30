package com.js.nowakelock.base

import android.util.Log

class LogUtil {
    companion object {
        private const val VERBOSE = 1 //啰嗦，等级最低的

        private const val DEBUG = 2 //调试

        private const val INFO = 3 //信息

        private const val WARN = 4 //警告

        private const val ERROR = 5 //错误

        private const val NOTHING = 6 //什么也不打印出来

        private const val level = DEBUG //LEVEL:标准


        fun v(tag: String, msg: String) {
            if (level <= VERBOSE) { //如果大于或者等于定义的标准就打印出来
                Log.v(tag, msg)
            }
        }

        fun d(tag: String, msg: String) {
            if (level <= DEBUG) {
                Log.d(tag, msg)
            }
        }

        fun i(tag: String, msg: String) {
            if (level <= INFO) {
                Log.i(tag, msg)
            }
        }

        fun w(tag: String, msg: String) {
            if (level <= WARN) {
                Log.w(tag, msg)
            }
        }

        fun e(tag: String, msg: String) {
            if (level <= ERROR) {
                Log.e(tag, msg)
            }
        }
    }
}