package com.js.nowakelock.ui.appDaS

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.js.nowakelock.R
import com.js.nowakelock.databinding.FragmentAppdasBinding
import com.js.nowakelock.ui.mainActivity.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class AppDaSFragment : Fragment() {
    private val layout = R.layout.item_appda

    private val mainViewModel: MainViewModel by sharedViewModel(named("MainVm"))
    private val viewModel: AppDaSViewModel by viewModel(named("AppDaSVM"))

    private lateinit var binding: FragmentAppdasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSubscription(viewModel.appDas)
        addSubscription(mainViewModel.type)
        addSubscription(mainViewModel.query)
        addSubscription(mainViewModel.sort)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAppdasBinding.inflate(inflater, container, false)
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
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        removeSubscription(viewModel.appDas)
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
    private fun list() = viewModel.appDas.value?.let { appDas ->
        mainViewModel.query.value?.let { query ->
            mainViewModel.sort.value?.let { sort ->
                mainViewModel.type.value?.let { type ->
                    viewModel.getList(
                        appDas, type, query, sort, layout
                    )
                }
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
            // 同步
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                viewModel.sync()
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