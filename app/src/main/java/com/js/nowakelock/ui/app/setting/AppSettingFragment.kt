package com.js.nowakelock.ui.app.setting

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.js.nowakelock.R
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.entity.AppInfo_st
import com.js.nowakelock.databinding.FragmentAppSettingBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class AppSettingFragment : Fragment() {

    private lateinit var packageName: String

    private val viewModel by inject<AppSettingViewModel> { parametersOf(packageName) }

    private lateinit var binding: FragmentAppSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        packageName = arguments?.getString("packageName") ?: ""
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        LogUtil.d("test1", packageName)

        binding = FragmentAppSettingBinding.inflate(inflater, container, false)
        context ?: return binding.root //if already create

//        viewModel = ViewModelProviders.of(this)[AppSettingViewModel::class.java]
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    private fun subscribe() {
        val observer = Observer<AppInfo_st> { appInfoSt ->
//            LogUtil.d("test13", "$appInfoSt}")
            binding.item = appInfoSt
        }
        viewModel.appInfoSt.observe(viewLifecycleOwner, observer)
        binding.handler = AppSettingHandler(viewModel)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val filter = menu.findItem(R.id.menu_filter)
        filter.isVisible = false
        val search = menu.findItem(R.id.search)
        search.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

}
