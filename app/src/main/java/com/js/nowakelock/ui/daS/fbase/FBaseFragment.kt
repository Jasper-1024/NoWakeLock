package com.js.nowakelock.ui.daS.fbase

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.js.nowakelock.R
import com.js.nowakelock.base.menuGone
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.databinding.FragmentDasBinding
import com.js.nowakelock.ui.mainActivity.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.androidx.viewmodel.ext.android.viewModel

open class FBaseFragment : Fragment() {

    open var packageName: String = ""
    open var userId: Int = 0
    open val type: Type = Type.Wakelock
    open val layout = R.layout.item_wakelock

    private val mainViewModel: MainViewModel by sharedViewModel(named("MainVm"))
    private lateinit var binding: FragmentDasBinding

    open val viewModel: FBaseViewModel by viewModel(named("FVm")) {
        parametersOf(packageName, userId, type)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        // 获取 packageName
        packageName = arguments?.getString("packageName") ?: ""
        userId = arguments?.getInt("userId") ?: 0

        super.onCreate(savedInstanceState)

        addSubscription(viewModel.das)
        addSubscription(mainViewModel.type)
        addSubscription(mainViewModel.query)
        addSubscription(mainViewModel.sort)

        viewModel.syncSt()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDasBinding.inflate(inflater, container, false)
        context ?: return binding.root

        //bind viewModel
        binding.vm = viewModel
        //lifecycleOwner
        //binding.lifecycleOwner = this
        binding.lifecycleOwner = viewLifecycleOwner
        //Divided Line
        setItemDecoration(binding.appList)
        // Refresh
        setSwipeRefreshLayout(binding.refresh)
        // Menu
        setMenu()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        removeSubscription(viewModel.das)
        removeSubscription(mainViewModel.type)
        removeSubscription(mainViewModel.query)
        removeSubscription(mainViewModel.sort)
    }


    //viewModel.list 添加订阅
    private fun <S> addSubscription(liveData: LiveData<S>) {
        viewModel.list.addSource(liveData) {
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                viewModel.list.postValue(list() ?: list())
            }
        }
    }

    //viewModel.list 取消订阅
    private fun <S> removeSubscription(liveData: LiveData<S>) {
        viewModel.list.removeSource(liveData)
    }

    //读取值
    private fun list() = viewModel.das.value?.let { apps ->
        mainViewModel.query.value?.let { query ->
            mainViewModel.sort.value?.let { sort ->
                viewModel.getList(
                    apps, query, sort, layout
                )
            }
        }
    }

    //SwipeRefresh
    private fun setSwipeRefreshLayout(swipeRefreshLayout: SwipeRefreshLayout) {
        swipeRefreshLayout.setDistanceToTriggerSync(600)
        //color
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE)
        // 关闭刷新
        swipeRefreshLayout.setOnRefreshListener {
            // 同步应用列表
            runBlocking {
                viewModel.syncInfos()
            }
            swipeRefreshLayout.isRefreshing = false
        }
    }

    // Divided Line
    private fun setItemDecoration(recyclerView: RecyclerView) = recyclerView.addItemDecoration(
        DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
    )

    open fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuGone(
                    menu,
                    setOf(
                        R.id.menu_filter_user,
                        R.id.menu_filter_system,
                        R.id.menu_filter_all
                    )
                )
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = true
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}