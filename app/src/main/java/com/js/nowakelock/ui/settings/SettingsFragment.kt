package com.js.nowakelock.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.js.nowakelock.R
import com.js.nowakelock.base.menuGone
import org.koin.android.ext.android.inject
import java.util.*


class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel by inject<SettingsViewModel>()

    private val readJson: Int = 42
    private val saveJson: Int = 43

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
//                    Toast.makeText(activity, "backup", Toast.LENGTH_LONG).show()
                    createFile("*/*", "NoWakeLock-Backup-${getData()}.json")
                    true
                }
        }

        val restore: Preference? = findPreference("restore")
        if (restore != null) {
            restore.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
//                    Toast.makeText(activity, "restore", Toast.LENGTH_LONG).show()
                    getJson()
                    true
                }
        }
    }

    private fun getJson() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        startActivityForResult(intent, readJson)
    }

    private fun createFile(mimeType: String, fileName: String) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = mimeType
            putExtra(Intent.EXTRA_TITLE, fileName)
        }

        startActivityForResult(intent, saveJson)
    }

    private fun getData(): String {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH) + 1
        val date: Int = c.get(Calendar.DATE)
        val hour: Int = c.get(Calendar.HOUR_OF_DAY)
        val minute: Int = c.get(Calendar.MINUTE)
        return "$year:$month:$date-$hour:$minute"
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {

        if (requestCode == readJson && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                viewModel.restore(uri)
            }
        }

        if (requestCode == saveJson && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                viewModel.backup(uri)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuGone(menu, setOf(R.id.menu_filter, R.id.search))
        super.onCreateOptionsMenu(menu, inflater)
    }
}
