package com.js.nowakelock.xposedhook.model

import java.io.Serializable

data class STModel(

    var recordFlag: Boolean = true,//allow record?

    var updateDb: Long = 30000,//update db time interval

    var updateST: Long = 60000,//update ST time interval

    var flagHM: HashMap<String, Boolean> = HashMap<String, Boolean>(), //flag

    var atIHM: HashMap<String, Long> = HashMap<String, Long>(), // allow time interval

    var rEHM: HashMap<String, Set<String>> = HashMap<String, Set<String>>() //re

) : Serializable