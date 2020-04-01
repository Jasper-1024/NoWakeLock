package com.js.nowakelock.abandoned

//    val binder = object : NWLService.Stub() {
//
//        override fun upCount(packageName: String?, wakelockName: String?) {
//            viewModelScope.launch {
//                if (packageName != null && wakelockName != null) {
//                    wakeLockRepository.upCount(packageName, wakelockName)
//                }
//            }
//        }
//
//        override fun upBlockCount(packageName: String?, wakelockName: String?) {
//            viewModelScope.launch {
//                if (packageName != null && wakelockName != null) {
//                    wakeLockRepository.upBlockCount(packageName, wakelockName)
//                }
//            }
//        }
//
//        override fun getFlag(packageName: String?, wakelockName: String?): Boolean {
//            var tmp = true
//            viewModelScope.launch {
//                if (packageName != null && wakelockName != null) {
//                    tmp = wakeLockRepository.getFlag(packageName, wakelockName)
//                }
//            }
//            return tmp
//        }
//
//    }