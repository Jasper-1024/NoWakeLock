package com.js.nowakelock.ui.appList

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.js.nowakelock.R
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.databinding.FragmentApplistBinding
import com.js.nowakelock.ui.databding.RecycleAdapter
import com.js.nowakelock.viewmodel.AppListViewModel
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class AppListFragment : Fragment() {

    private val viewModel: AppListViewModel by inject<AppListViewModel>()
    lateinit var binding: FragmentApplistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_app_list2, container, false)
        binding = FragmentApplistBinding.inflate(inflater, container, false)
        context ?: return binding.root
        //adapter
        val handler = AppListHandler()
        val adapter = RecycleAdapter(R.layout.item_appinfo, handler)
        binding.appinfoList.adapter = adapter
        subscribeUi(adapter)

        setItemDecoration(binding.appinfoList)

        //set SwipeRefresh
        setSwipeRefreshLayout(binding.appinfoRefresh)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout) {
        //
        swipeRefreshLayout.setDistanceToTriggerSync(300)
        //color
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE)
        //binding
        swipeRefreshLayout.setOnRefreshListener() {
            viewModel.syncAppInfos()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun subscribeUi(adapter: RecycleAdapter) {
        val observer = Observer<List<AppInfo>> { albinos ->
            adapter.submitList(albinos)
        }
        viewModel.appInfos.observe(viewLifecycleOwner, observer)
    }

    private fun setItemDecoration(recyclerView: RecyclerView) = recyclerView.addItemDecoration(
        DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
    )
}
