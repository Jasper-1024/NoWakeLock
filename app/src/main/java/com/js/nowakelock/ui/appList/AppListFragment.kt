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
import com.js.nowakelock.base.cache
import com.js.nowakelock.base.menuGone
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

    private val viewModel: AppListViewModel by inject()
    private lateinit var binding: FragmentApplistBinding
    private lateinit var adapter: RecycleAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentApplistBinding.inflate(inflater, container, false)
        context ?: return binding.root  //if already create
        //get MainViewModel
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
        //adapter
        subscribeUi()
        //appListStatus
        subscribeStatus()
    }

    private fun subscribeUi() {
        val observer = Observer<List<AppInfo>> { albinos ->
            loadAppList(albinos, mainViewModel.status.value)
        }
        viewModel.appInfos.observe(viewLifecycleOwner, observer)
    }

    private fun subscribeStatus() {
        val observer = Observer<cache> { status ->
            loadAppList(viewModel.appInfos.value, status)
        }
        mainViewModel.status.observe(viewLifecycleOwner, observer)
    }


    private fun loadAppList(appInfos: List<AppInfo>?, catch: cache?) {
        if (appInfos != null && catch != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitList(viewModel.list(appInfos, catch))
            }
        }
    }

    //SwipeRefresh
    private fun setSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.setDistanceToTriggerSync(300)
        //color
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE)
        //binding
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.syncAppInfos()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setItemDecoration(recyclerView: RecyclerView) = recyclerView.addItemDecoration(
        DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
    )

    //set toolbar menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        menuGone(menu, setOf(R.id.menu_sort_count))
        menuGone(menu, setOf(R.id.menu_sort_countime))
        super.onCreateOptionsMenu(menu, inflater)
    }

}
