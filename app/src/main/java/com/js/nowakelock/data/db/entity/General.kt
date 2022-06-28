package com.js.nowakelock.data.db.entity

data class General(
    var st: St,
    var info: Info = Info(name = st.name, type = st.type, packageName = st.packageName)
)
