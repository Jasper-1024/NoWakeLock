package com.js.nowakelock.ui.app.setting

import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.data.db.entity.AppInfo_st

class AppSettingHandler(var viewModel: AppSettingViewModel) : BaseHandler() {
    fun save(appInfoSt: AppInfo_st?) {
        viewModel.saveAppSetting(appInfoSt)
    }
}