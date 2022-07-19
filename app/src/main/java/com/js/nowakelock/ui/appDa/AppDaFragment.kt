package com.js.nowakelock.ui.appDa

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.js.nowakelock.R
import com.js.nowakelock.base.menuGone
import com.js.nowakelock.databinding.FragmentAppdaBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class AppDaFragment : Fragment() {
    var packageName: String = ""
    var userId: Int = 0

    private lateinit var binding: FragmentAppdaBinding

    val viewModel: AppDaViewModel by viewModel(named("AppDaVm")) {
        parametersOf(packageName, userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        packageName = arguments?.getString("packageName") ?: ""
        userId = arguments?.getInt("userId") ?: 0
        super.onCreate(savedInstanceState)

        viewModel.syncAppSt()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppdaBinding.inflate(inflater, container, false)
        context ?: return binding.root

        //bind viewModel
        binding.vm = viewModel
        //lifecycleOwner
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }


    private fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuGone(menu, setOf(R.id.menu_filter, R.id.search))
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}