package com.js.nowakelock.ui.service

import android.view.View
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.data.db.entity.Service

class ServiceHandler(var viewModel: ServiceViewModel) : BaseHandler() {
    fun onClick(view: View, service: Service) {
        viewModel.setFlag(service)
    }

    fun onTextChanged(service: Service) {
        viewModel.setFlag(service)
    }
}