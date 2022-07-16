package com.js.nowakelock.ui.da

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.js.nowakelock.R
import com.js.nowakelock.base.menuGone
import com.js.nowakelock.base.stringToType
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.databinding.FragmentDaBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class DaFragment : Fragment() {

    var name: String = ""
    var packageName: String = ""
    var type: Type = Type.UnKnow
    var userId: Int = 0

    private lateinit var binding: FragmentDaBinding

    val viewModel: DaViewModel by viewModel(named("DaVm")) {
        parametersOf(name, type, userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        name = arguments?.getString("name") ?: ""
        packageName = arguments?.getString("packageName") ?: ""
        arguments?.getString("type")?.let {
            type = stringToType(it)
        }
        userId = arguments?.getInt("userId") ?: 0

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaBinding.inflate(inflater, container, false)
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