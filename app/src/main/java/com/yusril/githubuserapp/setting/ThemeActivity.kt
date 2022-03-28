package com.yusril.githubuserapp.setting

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.yusril.githubuserapp.R

class ThemeActivity : Application() {
    override fun onCreate() {
        super.onCreate()


        val darkPreference = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val mode = darkPreference.getString(getString(R.string.pref_key_dark), "follow_system")

        AppCompatDelegate.setDefaultNightMode(
            when(mode) {
                getText(R.string.pref_dark_on) -> AppCompatDelegate.MODE_NIGHT_YES
                getText(R.string.pref_dark_off) -> AppCompatDelegate.MODE_NIGHT_NO
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
        )
    }
}