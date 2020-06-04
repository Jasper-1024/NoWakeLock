package com.js.nowakelock.ui.infofragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.js.nowakelock.databinding.FragmentInfoBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


class InfoFragment : Fragment() {

    private lateinit var name: String
    private lateinit var packageName: String
    private lateinit var type: String

    lateinit var binding: FragmentInfoBinding
    val viewModel: InfoViewModel by inject(named("VMI")) { parametersOf(name, packageName, type) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString("name") ?: ""
        packageName = arguments?.getString("packageName") ?: ""
        type = arguments?.getString("type") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        context ?: return binding.root //if already create

        binding.vm = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }
}