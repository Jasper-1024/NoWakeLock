package com.js.nowakelock.ui.settings

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.js.nowakelock.R
import com.js.nowakelock.base.menuGone


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val themePreference =
            findPreference<ListPreference>("theme_list")
        if (themePreference != null) {
            themePreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    val themeOption = newValue as String
                    ThemeHelper.applyTheme(themeOption)
                    true
                }
        }

        val backup: Preference? = findPreference("backup")
        if (backup != null) {
            backup.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    Toast.makeText(activity, "backup", Toast.LENGTH_LONG).show()
                    true
                }
        }

        val restore: Preference? = findPreference("restore")
        if (restore != null) {
            restore.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    Toast.makeText(activity, "restore", Toast.LENGTH_LONG).show()
                    true
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuGone(menu, setOf(R.id.menu_filter, R.id.search))
        super.onCreateOptionsMenu(menu, inflater)
    }
}
