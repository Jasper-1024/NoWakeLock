package com.js.nowakelock.ui.appList

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.js.nowakelock.R
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.databinding.FragmentApplistBinding
import com.js.nowakelock.ui.databding.RecycleAdapter
import com.js.nowakelock.ui.mainActivity.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class AppListFragment : Fragment() {

    private val viewModel: AppListViewModel by inject<AppListViewModel>()
    lateinit var binding: FragmentApplistBinding
    lateinit var adapter: RecycleAdapter
    lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_app_list2, container, false)
        binding = FragmentApplistBinding.inflate(inflater, container, false)
        context ?: return binding.root

//        mainViewModel = activity?.run {
//            ViewModelProviders(this).get[MainViewModel::class.java]
//        } ?: throw Exception("Invalid Activity")

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]


        //adapter
        val handler = AppListHandler(viewModel)
        adapter = RecycleAdapter(R.layout.item_appinfo, handler)
        binding.appinfoList.adapter = adapter
        subscribeUi()
        //分割线
        setItemDecoration(binding.appinfoList)

        //set SwipeRefresh
        setSwipeRefreshLayout(binding.appinfoRefresh)

        //appListStatus
        subscribeStatus()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout) {
        //
        swipeRefreshLayout.setDistanceToTriggerSync(300)
        //color
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE)
        //binding
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.syncAppInfos()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun subscribeUi() {
        val observer = Observer<List<AppInfo>> { albinos ->
            loadAppList(albinos, mainViewModel.appListStatus.value)
        }
        viewModel.appInfos.observe(viewLifecycleOwner, observer)
    }

    private fun subscribeStatus() {
        val observer = Observer<Int> { status ->
            loadAppList(viewModel.appInfos.value, status)
        }
        mainViewModel.appListStatus.observe(this as LifecycleOwner, observer)
    }

    private fun loadAppList(appInfos: List<AppInfo>?, status: Int?) {
        if (appInfos != null && status != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                when (status) {
                    1 -> adapter.submitList(viewModel.userAppList(appInfos))
                    2 -> adapter.submitList(viewModel.systemAppList(appInfos))
                    3 -> adapter.submitList(viewModel.countAppList(appInfos))
                    4 -> adapter.submitList(viewModel.appList(appInfos))
                }
            }
        }
    }

    private fun setItemDecoration(recyclerView: RecyclerView) = recyclerView.addItemDecoration(
        DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
    )
}
