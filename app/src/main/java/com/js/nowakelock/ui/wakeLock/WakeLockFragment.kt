package com.js.nowakelock.ui.wakeLock

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
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.databinding.FragmentWakeLockBinding
import com.js.nowakelock.ui.databding.RecycleAdapter
import com.js.nowakelock.ui.mainActivity.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class WakeLockFragment : Fragment() {

    private lateinit var packageName: String

    private val viewModel by inject<WakeLockViewModel> { parametersOf(packageName) }
    private lateinit var binding: FragmentWakeLockBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: RecycleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        packageName = arguments?.getString("packageName") ?: ""
        binding = FragmentWakeLockBinding.inflate(inflater, container, false)
        context ?: return binding.root //if already create

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        //set recyclerview
        val handler = WakeLockHandler(viewModel)
        adapter = RecycleAdapter(R.layout.item_wakelock, handler)
        binding.wakelockList.adapter = adapter

        setItemDecoration(binding.wakelockList)
        //set SwipeRefresh
        setSwipeRefreshLayout(binding.wakelockRefresh)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //adapter
        subscribeUi()
        //status
        subscribeStatus()
    }

    /**adapter subscribe data */
    private fun subscribeUi() {
        val observer = Observer<List<WakeLock>> { albinos ->
            loadWakeLockList(albinos, mainViewModel.status.value)
        }
        viewModel.wakeLocks.observe(viewLifecycleOwner, observer)
    }

    private fun subscribeStatus() {
        val observer = Observer<cache> { status ->
            loadWakeLockList(viewModel.wakeLocks.value, status)
        }
        mainViewModel.status.observe(viewLifecycleOwner, observer)
    }

    private fun loadWakeLockList(wakeLocks: List<WakeLock>?, cache: cache?) {
        if (wakeLocks != null && cache != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitList(viewModel.list(wakeLocks, cache))
            }
        }
    }


    private fun setItemDecoration(recyclerView: RecyclerView) = recyclerView.addItemDecoration(
        DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
    )

    //SwipeRefresh
    private fun setSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout) {
        //
        swipeRefreshLayout.setDistanceToTriggerSync(300)
        //color
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE)
        //binding
        swipeRefreshLayout.setOnRefreshListener {
//            viewModel.syncWakeLocks()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    //set toolbar menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val filterUser = menu.findItem(R.id.menu_filter_user)
        filterUser.isVisible = false
        val filterSystem = menu.findItem(R.id.menu_filter_system)
        filterSystem.isVisible = false
        val filterAll = menu.findItem(R.id.menu_filter_all)
        filterAll.isVisible = false
//        val sortCount = menu.findItem(R.id.menu_sort_count)
//        sortCount.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

}
