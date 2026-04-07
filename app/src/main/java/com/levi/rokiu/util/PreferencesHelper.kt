package com.levi.rokiu.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesHelper(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "roku_remote_prefs"
        private const val KEY_FIRST_LAUNCH = "first_launch"

        private const val LAST_DEVICE_URL = "last_device_url"
        private const val LAST_DEVICE_NAME = "last_device_name"
    }

    var isFirstLaunch: Boolean
        get() = prefs.getBoolean(KEY_FIRST_LAUNCH, true)
        set(value) = prefs.edit { putBoolean(KEY_FIRST_LAUNCH, value) }

    var lastDeviceUrl: String?
        get() = prefs.getString(LAST_DEVICE_URL, null)
        set(value) = prefs.edit { putString(LAST_DEVICE_URL, value) }

    var lastDeviceName: String?
        get() = prefs.getString(LAST_DEVICE_NAME, null)
        set(value) = prefs.edit { putString(LAST_DEVICE_NAME, value) }
}