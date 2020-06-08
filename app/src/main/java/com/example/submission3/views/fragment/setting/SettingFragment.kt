package com.example.submission3.views.fragment.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.submission3.R
import java.util.*

class SettingFragment : PreferenceFragmentCompat() {

    private lateinit var LANGUAGE: String
    private lateinit var languagePreference: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        val myPref: Preference? = findPreference(getString(R.string.change_language_settings)) as Preference?

        myPref?.setOnPreferenceClickListener(object : Preference.OnPreferenceClickListener {
            override fun onPreferenceClick(preference: Preference?): Boolean {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
                return true
            }
        })

        init()
    }

    private fun init() {
        val sh = preferenceManager.sharedPreferences
        val languagename: String = Locale.getDefault().getDisplayLanguage()
        LANGUAGE = resources.getString(R.string.change_language_settings)
        languagePreference = findPreference<Preference> (LANGUAGE) as Preference
        languagePreference.summary = sh.getString(LANGUAGE, languagename)
    }

}