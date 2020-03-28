package com.js.nowakelock.ui.wakeLock

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.js.nowakelock.R
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.databinding.FragmentWakelockBinding
import com.js.nowakelock.ui.databding.RecycleAdapter
import com.js.nowakelock.viewmodel.WakeLockViewModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass.
 */
class WakeLockFragment : Fragment() {

    //args ,which get PackageName
    private val args: WakeLockFragmentArgs by navArgs()

    private val viewModel by inject<WakeLockViewModel> { parametersOf(args.PackageName) }
    private lateinit var binding: FragmentWakelockBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWakelockBinding.inflate(inflater, container, false)
        context ?: return binding.root //if already create
        //set recyclerview
        val handler = WakeLockHandler()
        val adapter = RecycleAdapter(R.layout.item_wakelock, handler)
        binding.wakelockList.adapter = adapter
        subscribeUi(adapter)

        setItemDecoration(binding.wakelockList)
        //set SwipeRefresh
        setSwipeRefreshLayout(binding.wakelockRefresh)

        setHasOptionsMenu(true)
        return binding.root
    }

    /**adapter subscribe data */
    private fun subscribeUi(adapter: RecycleAdapter) {
        val observer = Observer<List<WakeLock>> { albinos ->
            adapter.submitList(albinos)
        }
        viewModel.wakelocks.observe(viewLifecycleOwner, observer)
    }

    private fun setItemDecoration(recyclerView: RecyclerView) = recyclerView.addItemDecoration(
        DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
    )

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
}
