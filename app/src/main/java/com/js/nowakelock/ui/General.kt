package com.js.nowakelock.ui

import com.js.nowakelock.data.db.entity.Info
import com.js.nowakelock.data.db.entity.St

data class General(
    var st: St,
    var info: Info = Info(name = st.name, type = st.type, packageName = st.packageName)
)
