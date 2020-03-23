package com.js.nowakelock.ui.WakeLock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.js.nowakelock.R
import com.js.nowakelock.viewmodel.WakeLockViewModel
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class WakeLockFragment : Fragment() {
    val ViewModel: WakeLockViewModel by inject<WakeLockViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wake_lock, container, false)
    }

}
