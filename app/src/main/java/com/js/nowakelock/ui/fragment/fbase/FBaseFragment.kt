package com.js.nowakelock.ui.fragment.fbase

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.databinding.FragmentDaBinding
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
    open val type: Type = Type.Wakelock

    private val mainViewModel: MainViewModel by sharedViewModel(named("MainVm"))
    private lateinit var binding: FragmentDaBinding

    open val viewModel: FBaseViewModel by viewModel(named("FVm")) {
        parametersOf(packageName, type)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        // 获取 packageName
        packageName = arguments?.getString("packageName") ?: ""
        super.onCreate(savedInstanceState)

        addSubscription(viewModel.das)
        addSubscription(mainViewModel.type)
        addSubscription(mainViewModel.query)
        addSubscription(mainViewModel.sort)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDaBinding.inflate(inflater, container, false)
        context ?: return binding.root

//        //bind viewModel
        binding.vm = viewModel
        //lifecycleOwner
        binding.lifecycleOwner = this
        //Divided Line
        setItemDecoration(binding.appList)
        // Refresh
        setSwipeRefreshLayout(binding.refresh)

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
                    apps, query, sort
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
}