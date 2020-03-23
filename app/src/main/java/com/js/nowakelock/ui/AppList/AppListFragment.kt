package com.js.nowakelock.ui.AppList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.js.nowakelock.R
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.databinding.FragmentAppListBinding
import com.js.nowakelock.ui.databding.RecycleAdapter
import com.js.nowakelock.viewmodel.AppListViewModel
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class AppListFragment : Fragment() {

    val viewModel: AppListViewModel by inject<AppListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_app_list2, container, false)
        val binding = FragmentAppListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = RecycleAdapter(R.layout.item_appinfo)
        binding.appinfoList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun subscribeUi(adapter: RecycleAdapter) {
        val observer = Observer<List<AppInfo>> { s ->
            adapter.submitList(s)
        }
        viewModel.appInfos.observe(viewLifecycleOwner, observer)
    }
}
