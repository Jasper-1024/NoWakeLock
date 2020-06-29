package com.js.nowakelock.ui.fragment.fbase

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.js.nowakelock.R
import com.js.nowakelock.base.cache
import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.databinding.FragmentBinding
import com.js.nowakelock.ui.databding.RecycleAdapter
import com.js.nowakelock.ui.databding.StringDetailsLookup
import com.js.nowakelock.ui.databding.StringKeyProvider
import com.js.nowakelock.ui.mainActivity.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


open class FFragment : Fragment() {

    open lateinit var packageName: String
    open val type: String = "wakelock"

    open val viewModel: FViewModel by inject(named("VMA")) { parametersOf(packageName) }
    open val itemLayout: Int = R.layout.item

    open lateinit var binding: FragmentBinding
    open lateinit var mainViewModel: MainViewModel

    private lateinit var recyclerView: RecyclerView
    open lateinit var adapter: RecycleAdapter
    private lateinit var tracker: SelectionTracker<String>

    private var actionMode: ActionMode? = null
    private var selectKeys: List<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        packageName = arguments?.getString("packageName") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root //if already create

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recyclerView = binding.list

        //set recyclerview
        setAdapter()
        setItemDecoration(recyclerView)

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

    private fun setAdapter() {
        val handler = FHandler(viewModel, type)
        adapter = RecycleAdapter(itemLayout, handler)
        binding.list.adapter = adapter

        setAdapterTracker()
    }

    private fun setAdapterTracker() {
        tracker = SelectionTracker.Builder<String>(
            "MySelection",
            recyclerView,
            StringKeyProvider(adapter),
            StringDetailsLookup(recyclerView),
            StorageStrategy.createStringStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        )
            .build()

        tracker.addObserver(
            object : SelectionTracker.SelectionObserver<String>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    if (!tracker.selection.isEmpty) {
                        selectKeys = tracker.selection.toList()
                    }

                    // Called when the user long-clicks on someView
                    when (actionMode) {
                        null -> {
                            val toolbar = activity?.findViewById(R.id.toolbar) as Toolbar
                            actionMode = toolbar.startActionMode(actionModeCallback)
                        }
                    }

                }
            })

        adapter.tracker = tracker
    }


    /**adapter subscribe data */
    private fun subscribeUi() {
        val observer = Observer<List<Item>> { albinos ->
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

    private fun loadList(items: List<Item>?, cache: cache?) {
        if (items != null && cache != null) {
            viewLifecycleOwner.lifecycleScope.launch {
//                LogUtil.d("test1","$items")
                adapter.submitList(viewModel.list(items, cache))
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
        swipeRefreshLayout.setDistanceToTriggerSync(300)
        //color
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE)
        //binding
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.action_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.action_allow_all -> {
                    viewModel.setItemStsFlag(selectKeys, true)
//                    mode.finish()
                    true
                }
                R.id.action_block_all -> {
                    viewModel.setItemStsFlag(selectKeys, false)
//                    mode.finish()
                    true
                }
                R.id.action_select_all -> {
                    viewModel.list.value?.forEach {
                        adapter.tracker?.select(it.info.name)
                    }
                    true
                }
                R.id.action_select_inverse -> {
                    selectKeys.forEach {
                        adapter.tracker?.deselect(it)
                    }
                    true
                }
                else -> false
            }
        }

        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {

            selectKeys.forEach {
                adapter.tracker?.deselect(it)
            }
            actionMode = null
        }
    }

}
