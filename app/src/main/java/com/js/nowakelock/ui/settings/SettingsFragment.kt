package com.js.nowakelock.ui.settings

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.js.nowakelock.R
import com.js.nowakelock.base.LogUtil


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val themePreference =
            findPreference<ListPreference>("theme_list")
        if (themePreference != null) {
            themePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, newValue ->
                    val themeOption = newValue as String

//                    if (themeOption.equals("black")) {
//                        activity?.setTheme(R.style.Black)
//                    }
//                    LogUtil.d("test1", themeOption)

                    ThemeHelper.applyTheme(themeOption)
                    true
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val filter = menu.findItem(R.id.menu_filter)
        filter.isVisible = false
        val search = menu.findItem(R.id.search)
        search.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }
}
