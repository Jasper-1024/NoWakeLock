package com.js.nowakelock.ui.help

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import com.js.nowakelock.R
import com.js.nowakelock.databinding.FragmentHelpBinding
import org.koin.android.ext.android.inject

class HelpFragment : Fragment() {

    private val viewModel by inject<HelpViewModel>()

    private lateinit var binding: FragmentHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpBinding.inflate(inflater, container, false)
        context ?: return binding.root
        binding.help = viewModel.help
        binding.tvInstructions.movementMethod = LinkMovementMethod.getInstance()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val filter = menu.findItem(R.id.menu_filter)
        filter.isVisible = false
        val search = menu.findItem(R.id.search)
        search.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }
}
