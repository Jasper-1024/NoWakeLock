package com.js.nowakelock.ui.appList.list

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.js.nowakelock.R
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.databinding.FragmentAppListWakeLockBinding
import com.js.nowakelock.ui.databding.RecycleAdapter
import com.js.nowakelock.ui.mainActivity.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
class ALWakeLockFragment : Fragment() {

    //args ,which get PackageName
    private val args: ALWakeLockFragmentArgs by navArgs()
    private lateinit var packageName: String

    private val viewModel by inject<ALWakeLockViewModel> { parametersOf(args.PackageName) }
    private lateinit var binding: FragmentAppListWakeLockBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: RecycleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //get packageName
        packageName = args.PackageName
        binding = FragmentAppListWakeLockBinding.inflate(inflater, container, false)
        context ?: return binding.root //if already create

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        //set recyclerview
        val handler =
            ALWakeLockHandler(viewModel)
        adapter = RecycleAdapter(R.layout.item_alwakelock, handler)
        binding.alwakelockList.adapter = adapter

        setItemDecoration(binding.alwakelockList)
        //set SwipeRefresh
        setSwipeRefreshLayout(binding.alwakelockRefresh)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //adapter
        subscribeUi()
        //appListStatus
        subscribeStatus()
        //searchView
        subscribSearch()
    }

    /**adapter subscribe data */
    private fun subscribeUi() {
        val observer = Observer<List<WakeLock>> { albinos ->
            loadWakeLockList(albinos, mainViewModel.status.value, mainViewModel.searchText.value)
        }
        viewModel.wakeLocks.observe(viewLifecycleOwner, observer)
    }

    private fun subscribeStatus() {
        val observer = Observer<Int> { status ->
            loadWakeLockList(viewModel.wakeLocks.value, status, mainViewModel.searchText.value)
        }
        mainViewModel.status.observe(viewLifecycleOwner, observer)
    }

    private fun subscribSearch() {
        val observer = Observer<String> { query ->
            loadWakeLockList(viewModel.wakeLocks.value, mainViewModel.status.value, query)
        }
        mainViewModel.searchText.observe(viewLifecycleOwner, observer)
    }

    private fun loadWakeLockList(
        wakeLocks: List<WakeLock>?,
        status: Int? = 4,
        query: String? = ""
    ) {
        if (wakeLocks != null && status != null && query != null) {
            viewLifecycleOwner.lifecycleScope.launch {
                adapter.submitList(viewModel.WakeLockList(wakeLocks, status, query))
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
            viewModel.syncWakeLocks()
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
