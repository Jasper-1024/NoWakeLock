package com.js.nowakelock.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.js.nowakelock.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}
