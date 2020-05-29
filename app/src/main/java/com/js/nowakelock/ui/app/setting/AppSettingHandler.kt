package com.js.nowakelock.ui.app.setting

import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.data.db.entity.AppInfoSt

class AppSettingHandler(var viewModel: AppSettingViewModel) : BaseHandler() {
    fun save(appInfoSt: AppInfoSt?) {
        viewModel.saveAppSetting(appInfoSt)
    }
}