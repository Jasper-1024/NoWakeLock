package com.js.nowakelock.ui.settings

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.js.nowakelock.R
import com.js.nowakelock.base.SPTools
import com.js.nowakelock.base.menuGone
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingViewModel by viewModel(named("SettingVm"))

    private val readJson: Int = 42
    private val saveJson: Int = 43

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMenu()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        //主题切换
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

        //debug
        val debugPreference = findPreference<SwitchPreferenceCompat>("debug")
        if (debugPreference != null) {
            debugPreference.onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { _, newValue ->
                    val themeOption = newValue as Boolean
                    SPTools.setBoolean("debug", themeOption)
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

    @Deprecated("Deprecated in Java")
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
}