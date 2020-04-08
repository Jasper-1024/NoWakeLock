package com.js.nowakelock.ui.appList

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        //adapter
        val handler = AppListHandler(viewModel)
        adapter = RecycleAdapter(R.layout.item_appinfo, handler)
        binding.appinfoList.adapter = adapter

        //分割线
        setItemDecoration(binding.appinfoList)

        //set SwipeRefresh
        setSwipeRefreshLayout(binding.appinfoRefresh)


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        subscribeUi()
        //appListStatus
        subscribeStatus()
        //searchView
        subscribSearch()
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
            loadAppList(albinos, mainViewModel.appListStatus.value, mainViewModel.searchText.value)
        }
        viewModel.appInfos.observe(viewLifecycleOwner, observer)
    }

    private fun subscribeStatus() {
        val observer = Observer<Int> { status ->
            loadAppList(viewModel.appInfos.value, status, mainViewModel.searchText.value)
        }
        mainViewModel.appListStatus.observe(viewLifecycleOwner, observer)
    }

    private fun subscribSearch() {
        val observer = Observer<String> { query ->
            loadAppList(viewModel.appInfos.value, mainViewModel.appListStatus.value, query)
        }
        mainViewModel.searchText.observe(viewLifecycleOwner, observer)
    }

    private fun loadAppList(appInfos: List<AppInfo>?, status: Int? = 1, query: String? = "") {
        if (appInfos != null && status != null && query != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitList(viewModel.AppList(appInfos, status, query))
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
