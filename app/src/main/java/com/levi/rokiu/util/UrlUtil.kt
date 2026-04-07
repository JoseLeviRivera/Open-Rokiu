package com.levi.rokiu.util

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.net.toUri

object UrlUtil {

    fun Context.navigateToURL(url: String) {
        if (url.isBlank()) return
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Log.d("DEBUG", "No app found to open this link")
        }
    }
}