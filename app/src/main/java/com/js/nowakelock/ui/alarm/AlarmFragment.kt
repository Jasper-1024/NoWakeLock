package com.js.nowakelock.ui.alarm

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
import com.js.nowakelock.data.db.entity.Alarm
import com.js.nowakelock.databinding.FragmentAlarmBinding
import com.js.nowakelock.ui.databding.RecycleAdapter
import com.js.nowakelock.ui.mainActivity.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AlarmFragment : Fragment() {

    private lateinit var packageName: String

    private val viewModel by inject<AlarmViewModel> { parametersOf(packageName) }

    private lateinit var binding: FragmentAlarmBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: RecycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        packageName = arguments?.getString("packageName") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlarmBinding.inflate(inflater, container, false)
        context ?: return binding.root //if already create

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        //set recyclerview
        val handler = AlarmHandler(viewModel)
        adapter = RecycleAdapter(R.layout.item_alarm, handler)
        binding.list.adapter = adapter

        setItemDecoration(binding.list)

        //set SwipeRefresh
        setSwipeRefreshLayout(binding.refresh)

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
        val observer = Observer<List<Alarm>> { albinos ->
            loadList(albinos, mainViewModel.status.value)
        }
        viewModel.list.observe(viewLifecycleOwner, observer)
    }

    private fun subscribeStatus() {
        val observer = Observer<cache> { status ->
            loadList(viewModel.list.value, status)
        }
        mainViewModel.status.observe(viewLifecycleOwner, observer)
    }

    private fun loadList(alarms: List<Alarm>?, cache: cache?) {
        if (alarms != null && cache != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitList(viewModel.list(alarms, cache))
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
        super.onCreateOptionsMenu(menu, inflater)
    }

}
